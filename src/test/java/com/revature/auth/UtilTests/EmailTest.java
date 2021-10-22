package com.revature.auth.UtilTests;

import com.revature.auth.dtos.UserDTO;
import com.revature.auth.entities.User;
import com.revature.auth.repos.UserRepo;
import com.revature.auth.services.UserService;
import com.revature.auth.utils.EmailUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sun.jvm.hotspot.utilities.Assert;

import java.util.HashSet;
import java.util.Set;

@Transactional
@SpringBootTest
public class EmailTest {

    @Autowired
    UserRepo userRepo;


    @Test
    void notify_user(){
        User testUser = userRepo.findById(23).get();
        UserDTO userDTO = new UserDTO(testUser);

        boolean result = EmailUtil.notifyUser(userDTO, "https://www.google.com/");
        Assertions.assertTrue(result);
    }

    @Test
    void notify_admin(){
        User testUser = userRepo.findById(24).get();
        UserDTO userDTO = new UserDTO(testUser);
        Set<UserDTO> admins = new HashSet<>();
        admins.add(userDTO);

        boolean result = EmailUtil.notifyAdmins(admins);
        Assertions.assertTrue(result);
    }
}
