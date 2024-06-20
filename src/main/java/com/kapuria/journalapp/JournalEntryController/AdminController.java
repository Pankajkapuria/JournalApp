package com.kapuria.journalapp.JournalEntryController;

import com.kapuria.journalapp.Service.UserServices;
import com.kapuria.journalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;


    @GetMapping("/get/users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userServices.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>("no user all found in database", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create/admin/user")
    public ResponseEntity<?> createAdminUser(@RequestBody User user){
        try{
            userServices.saveNewAdmin(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
}
