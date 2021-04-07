package com.revature.auth.ServiceTests;

import com.revature.auth.entities.User;
import com.revature.auth.repos.UserRepo;
import com.revature.auth.services.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Autowired
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepo userRepo;


    @Test
    @Order(1)
    void register_user() {
        Mockito.when(userRepo.save(any())) // when the userRepo.save() method is called with "any()" parameter
            .thenAnswer((Answer<User>) invocation -> {
                User u = invocation.getArgument(0); // get the first argument/parameter sent to the save() method

                // do whatever logic the save() method would normally do
                u.setUserId(1);

                // return what you expect the save() method to return
                return u;
            });


        // Here we do test logic
        User testUser = new User();
        testUser.setEmail("test@email.com");
        testUser.setRole("trainer");

        testUser = userService.register(testUser);
        Assertions.assertEquals("pending", testUser.getStatus());

        System.out.println(testUser);

    }

    @Test
    @Order(2)
    void get_users_by_status() {
        Set<User> users = new HashSet<>();

        User user1 = new User(1, "test1@gmail.com", "password", "TEST", "ADMIN");
        User user2 = new User(2, "test2@gmail.com", "password", "TEST", "ADMIN");
        User user3 = new User(3, "test3@gmail.com", "password", "PENDING", "ADMIN");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        Mockito.when(userRepo.findAllByStatus(any())).thenReturn(users);
        Set<User> resultSet = userService.getUsersByStatus("TEST");
        System.out.println(resultSet);

        Assertions.assertTrue(resultSet.size() > 0);
        System.out.println(resultSet);

    }

    @Test
    @Order(3)
    void approve_user() {
        User user1 = new User(1, "test1@gmail.com", "password", "pending", "ADMIN");
        Mockito.when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepo.save(any())).thenReturn(user1);

        userService.approveUser(user1, "testpassword");

        System.out.println(user1);
        Assertions.assertEquals("approved", user1.getStatus());
    }

    @Test
    @Order(4)
    void approve_user_by_id() {
        User user1 = new User(1, "test1@gmail.com", "password", "pending", "ADMIN");
        Mockito.when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepo.save(any())).thenReturn(user1);
        System.out.println(userService.approveUserById(1, "testpassword"));
        Assertions.assertEquals("approved", user1.getStatus());
    }

    @Test
    @Order(5)
    void deny_user() {
        User user1 = new User(1, "test1@gmail.com", "password", "pending", "ADMIN");
        Mockito.when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepo.save(any())).thenReturn(user1);

        userService.denyUser(user1);

        System.out.println(user1);
        Assertions.assertEquals("denied", user1.getStatus());
    }

    @Test
    @Order(6)
    void deny_user_by_id() {
        User user1 = new User(1, "test1@gmail.com", "password", "pending", "ADMIN");
        Mockito.when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepo.save(any())).thenReturn(user1);
        System.out.println(userService.denyUserById(1));
        Assertions.assertEquals("denied", user1.getStatus());
    }

    @Test
    @Order(7)
    void set_password() {
        User user1 = new User(1, "test1@gmail.com", "", "pending", "ADMIN");
        Mockito.when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepo.save(any())).thenReturn(user1);

        userService.setPassword(user1, "somepass");

        System.out.println(user1.getPassword());
        Assertions.assertNotEquals("somepass", user1.getPassword());
    }

    @Test
    @Order(8)
    void set_password_by_id() {
        User user1 = new User(1, "test1@gmail.com", "", "pending", "ADMIN");
        Mockito.when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepo.save(any())).thenReturn(user1);

        userService.setPasswordById(1, "somepass");

        System.out.println(user1.getPassword());
        Assertions.assertNotEquals("somepass", user1.getPassword());
    }

    @Test
    @Order(9)
    void check_cred() {
        User user1 = new User(1, "test1@gmail.com", "3dfffde8ad041c96edc27520eb0519130983ecb55ef0bfae2574aac051181c5a", "pending", "ADMIN");
        Mockito.when(userRepo.findByEmail(any())).thenReturn(user1);

        System.out.println(user1);
        Assertions.assertNotNull(userService.findUserByUsernameAndPassword("test1@gmail.com", "password"));
        //Assertions.assertNull(userService.findUserByUsernameAndPassword("test1@gmail.com", "wrongpass"));
    }

    @Test
    @Order(10)
    void get_users_by_role() {
        Set<User> users = new HashSet<>();

        User user1 = new User(1, "test1@gmail.com", "password", "TEST", "ADMIN");
        User user2 = new User(2, "test2@gmail.com", "password", "TEST", "NOT ADMIN");
        User user3 = new User(3, "test3@gmail.com", "password", "PENDING", "ADMIN");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        Mockito.when(userRepo.findAllByRole(any())).thenReturn(users);
        Set<User> resultSet = userService.getUsersByRole("NOT ADMIN");
        System.out.println(resultSet);

        Assertions.assertTrue(resultSet.size() != 0);
        System.out.println(resultSet);

    }

    @Test
    @Order(11)
    void get_user_by_id() {
        User user1 = new User(1, "test1@gmail.com", "password", "TEST", "ADMIN");

        Mockito.when(userRepo.findById(any())).thenReturn(java.util.Optional.of(user1));
        User testUser = userService.getUserById(1);
        System.out.println(testUser);

        Assertions.assertNotNull(testUser);
        System.out.println(testUser);

    }
}
