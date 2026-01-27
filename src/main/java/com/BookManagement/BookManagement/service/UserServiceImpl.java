package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.User;
import com.BookManagement.BookManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Override
    public User register(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        return userRepo.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        if(user == null || !user.getPassword().equals(password)){
            throw new RuntimeException("Wrong Username or password");
        }
        return userRepo.findByUsernameAndPassword(username, password);
    }
}
