package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.UserDetails;

public interface UserDetailService {

    UserDetails createUserDetail(Long userId, UserDetails userDetails);
}
