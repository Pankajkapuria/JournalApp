package com.kapuria.journalapp.Service;

import com.kapuria.journalapp.entity.User;
import com.kapuria.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Disabled
    @Test
    public void testFindByUserName(){
        User pankajKapuria = userRepository.findByUserName("pankaj");

        assertNotNull(pankajKapuria);
    }

    @ParameterizedTest
    @ValueSource( strings ={
            "pankaj",
            "pankajkapuria",
            "pankajKapuria"
    })
    public void test(String userName){
        User user = userServices.findByUserName(userName);

        assertNotNull(user);
    }
}
