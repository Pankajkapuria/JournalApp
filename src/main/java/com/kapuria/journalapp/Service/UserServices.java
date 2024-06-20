package com.kapuria.journalapp.Service;

import com.kapuria.journalapp.entity.User;
import com.kapuria.journalapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean saveNewEntry(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("User"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            log.error("error occur when add new user {} ",user.getUserName());
            log.warn("error occur when add new user {} ",user.getUserName());
            log.info("error occur when add new user {}",user.getUserName());
            log.debug("error occur when add new user {}",user.getUserName());
            log.trace("error occur when add new user {}",user.getUserName());
            return false;
        }
    }

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public boolean saveNewAdmin(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("User","ADMIN"));
            userRepository.save(user);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByIds(ObjectId id){
        return userRepository.findById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
}

