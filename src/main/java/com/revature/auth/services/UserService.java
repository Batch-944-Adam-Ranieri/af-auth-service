package com.revature.auth.services;

import com.revature.auth.entities.User;

import java.util.Set;

public interface UserService {

    public User register(User user);
    public Set<User> getUsersByStatus(String status);
    public Set<User> getUsersByRole(String role);
    public User getUserById(int userId);

    public User approveUser(User user, String pass);
    public User approveUserById(int userId, String pass);

    public User denyUser(User user);
    public User denyUserById(int userId);

    public User setPassword(User user, String password);
    public User setPasswordById(int userId, String password);

    public User findUserByUsernameAndPassword(String username, String password);

}
