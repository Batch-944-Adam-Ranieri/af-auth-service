package com.revature.auth.services;

import com.revature.auth.entities.User;
import com.revature.auth.repos.UserRepo;
import com.revature.auth.utils.HashUtil;
import com.revature.auth.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Component
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public User register(User user) {
        user.setStatus("pending_approval");
        user = userRepo.save(user);
        return user;
    }

    @Override
    public Set<User> getUsersByStatus(String status) {
        return userRepo.findAllByStatus(status);
    }

    @Override
    public Set<User> getUsersByRole(String role) {
            return userRepo.findAllByRole(role);
    }

    @Override
    public User getUserById(int userId) {
        return userRepo.findById(userId).get();
    }


    @Override
    public User approveUser(User user, String pass) {
        Optional<User> op = userRepo.findById(user.getUserId());
        if (op.isPresent()) {
            User userCheck = op.get();
            userCheck.setStatus("pending_creation");
            userCheck.setPassword(HashUtil.hash(pass));
            userRepo.save(userCheck);
            return userCheck;
        }
        return null;
    }

    @Override
    public User approveUserById(int userId, String pass) {
        Optional<User> op = userRepo.findById(userId);
        if (op.isPresent()) {
            User user = op.get();
            user.setStatus("pending_creation");
            user.setPassword(HashUtil.hash(pass));
            userRepo.save(user);
            return user;
        }
        return null;
    }

    @Override
    public User denyUser(User user) {
        Optional<User> op = userRepo.findById(user.getUserId());
        if (op.isPresent()) {
            User userCheck = op.get();
            userCheck.setStatus("denied");
            userRepo.save(userCheck);
            return userCheck;
        }
        return null;
    }

    @Override
    public User denyUserById(int userId) {
        Optional<User> op = userRepo.findById(userId);
        if (op.isPresent()) {
            User user = op.get();
            user.setStatus("denied");
            userRepo.save(user);
            return user;
        }
        return null;
    }

    @Override
    public User setPassword(User user, String password) {
        Optional<User> op = userRepo.findById(user.getUserId());
        if (!op.isPresent()) throw new IllegalArgumentException("user not found");

        User user1 = op.get();
        user1.setPassword(HashUtil.hash(password));
        user1.setStatus("created");
        userRepo.save(user1);
        return user1;
    }

    @Override
    public User setPasswordById(int userId, String password) {
        Optional<User> op = userRepo.findById(userId);
        if (op.isPresent()) {
            User user1 = op.get();
            user1.setPassword(HashUtil.hash(password));
            user1.setStatus("created");
            userRepo.save(user1);
            return user1;
        }
        return null;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
//        Optional<User> op = userRepo.findByEmail(username);
        User user = userRepo.findByEmail(username);
        if (HashUtil.hash(password).equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}
