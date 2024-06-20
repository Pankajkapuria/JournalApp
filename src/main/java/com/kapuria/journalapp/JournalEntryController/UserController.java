package com.kapuria.journalapp.JournalEntryController;

import com.kapuria.journalapp.Service.UserServices;
import com.kapuria.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id){
        Optional<User> user=userServices.findByIds(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User new_data ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User old_data=userServices.findByUserName(authentication.getName());
         if(old_data!=null){
             old_data.setUserName(!new_data.getUserName().equals("") ? new_data.getUserName() : old_data.getUserName());
             old_data.setPassword(!new_data.getPassword().equals("") ? new_data.getPassword() : old_data.getPassword());
             userServices.saveNewEntry(old_data);
             return new ResponseEntity<>(old_data,HttpStatus.OK);
         }
        return new ResponseEntity<>("This user content not found",HttpStatus.NOT_FOUND);
    }


}
