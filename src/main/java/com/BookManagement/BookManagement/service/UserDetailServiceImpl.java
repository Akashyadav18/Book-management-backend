package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.User;
import com.BookManagement.BookManagement.entity.UserDetails;
import com.BookManagement.BookManagement.repository.UserDetailRepository;
import com.BookManagement.BookManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService{

    @Autowired
    private UserDetailRepository userDetailRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails createUserDetail(Long userId, UserDetails userDetails) {
        User user = userRepo.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        userDetails.setUser(user);
        return userDetailRepo.save(userDetails);
    }
}
