package com.kapuria.journalapp.JournalEntryController;

import com.kapuria.journalapp.Service.JournalEntryServices;
import com.kapuria.journalapp.Service.UserServices;
import com.kapuria.journalapp.entity.JournalEntry;
import com.kapuria.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryServices journalEntryServices;
    @Autowired
    private UserServices userServices;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = userServices.findByUserName(authentication.getName());
        if(user==null){
            return new ResponseEntity<>("user not present of this user name",HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> all= user.getJournalEntries();
        if(all!=null && all.size()>0){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>("no journal entry found for this user",HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry>  createEntry(
            @RequestBody JournalEntry my_data
    ){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            my_data.setDate(LocalDateTime.now());
            journalEntryServices.saveEntry(my_data,authentication.getName());
            return new ResponseEntity<>(my_data,HttpStatus.CREATED);
        } catch ( Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userServices.findByUserName(userName);
        List<JournalEntry> entries = user.getJournalEntries().stream().filter(x -> x.get_id().equals(myId)).toList();
        if(entries.size()>0){
            Optional<JournalEntry> journalEntry = journalEntryServices.findByIds(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Enter valid id", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(
            @PathVariable ObjectId myId
    ){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            return journalEntryServices.deleteById(myId,userName);

        }catch (Exception e){
            System.out.println(e);
            return new  ResponseEntity<>("something wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry new_entity
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userServices.findByUserName(userName);
        List<JournalEntry> entries = user.getJournalEntries().stream().filter(x -> x.get_id().equals(id)).toList();
        if(!entries.isEmpty()){
            JournalEntry oldEntry= entries.get(0);
            if(oldEntry!=null){
                oldEntry.setTitle(new_entity.getTitle()!=null && !new_entity.getTitle().equals("") ? new_entity.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(new_entity.getContent()!=null && !new_entity.getContent().equals("") ? new_entity.getContent() : oldEntry.getContent());
                journalEntryServices.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }



        return new ResponseEntity<>("This id content not found",HttpStatus.NOT_FOUND);
    }
}
