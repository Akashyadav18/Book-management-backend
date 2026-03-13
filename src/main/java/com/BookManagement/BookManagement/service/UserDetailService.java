package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.UserDetails;

import java.util.List;

public interface UserDetailService {

    UserDetails createUserDetail(Long userId, UserDetails userDetails);

    UserDetails getAllUserDetails(Long userId);

    void deleteUserDetail(Long id);

    UserDetails updateUserDetail(Long userId, UserDetails userDetails);
}
