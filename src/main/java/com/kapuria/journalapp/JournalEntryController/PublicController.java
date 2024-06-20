package com.kapuria.journalapp.JournalEntryController;

import com.kapuria.journalapp.Service.UserServices;
import com.kapuria.journalapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserServices userServices;

    @GetMapping("health-check")
    public String Health_Check(){
        return "ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            boolean newEntry = userServices.saveNewEntry(user);
            if(newEntry){
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("user is not created", HttpStatus.BAD_REQUEST);
        } catch ( Exception e){
           log.error("user is not created");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
