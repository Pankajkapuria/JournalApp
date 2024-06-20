package com.kapuria.journalapp.Service;

import com.kapuria.journalapp.entity.User;
import com.kapuria.journalapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
public class userDetailsServiceMplTests {

    @InjectMocks
    private userDetailsServiceMpl userDetailsServiceMpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void loadUserByUsernameTest(){
         when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("pankaj").password("cnvccc").roles(new ArrayList<>()).build());

         UserDetails userDetails = userDetailsServiceMpl.loadUserByUsername("pankaj");
         assertNotNull(userDetails);
     }

}
