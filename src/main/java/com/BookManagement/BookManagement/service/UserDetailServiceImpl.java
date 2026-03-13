package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.User;
import com.BookManagement.BookManagement.entity.UserDetails;
import com.BookManagement.BookManagement.repository.UserDetailRepository;
import com.BookManagement.BookManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserDetails getAllUserDetails(Long userId) {
        return userDetailRepo.findByUserId(userId);
    }

    @Override
    public void deleteUserDetail(Long id) {
        if(!userDetailRepo.existsById(id)){
            throw new RuntimeException("User Detail not found with id: "+ id);
        }
        userDetailRepo.deleteById(id);
    }

    @Override
    public UserDetails updateUserDetail(Long userId, UserDetails userDetails) {
        UserDetails existingUser = userDetailRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id"+userId));
        existingUser.setUserName(userDetails.getUserName());
        existingUser.setName(userDetails.getName());
        existingUser.setPassword(userDetails.getPassword());
        existingUser.setAge(userDetails.getAge());
        existingUser.setSalary(userDetails.getSalary());
        existingUser.setAccountBalance(userDetails.getAccountBalance());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setCorporateEmail(userDetails.getCorporateEmail());
        existingUser.setDob(userDetails.getDob());
        existingUser.setUserExpiry(userDetails.getUserExpiry());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());

        return userDetailRepo.save(existingUser);
    }
}
