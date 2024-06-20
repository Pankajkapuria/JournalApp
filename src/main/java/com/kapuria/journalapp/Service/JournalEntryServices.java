package com.kapuria.journalapp.Service;

import com.kapuria.journalapp.entity.JournalEntry;
import com.kapuria.journalapp.entity.User;
import com.kapuria.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryServices {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserServices userServices;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user=userServices.findByUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            System.out.println(user.getJournalEntries());
            user.getJournalEntries().add(saved);
            userServices.saveEntry(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        JournalEntry saved = journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findByIds(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<?> deleteById(ObjectId id, String userName){
        User user=userServices.findByUserName(userName);
        boolean present = user.getJournalEntries().removeIf(x -> x.get_id().equals(id));
        if(present){
            userServices.saveEntry(user);
            journalEntryRepository.deleteById(id);
            return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new  ResponseEntity<>("Enter valid id",HttpStatus.BAD_REQUEST);
    }
}
