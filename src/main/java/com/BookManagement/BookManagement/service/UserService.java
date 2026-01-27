package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.User;

public interface UserService {
    User register(User user);

    User login(String username, String password);
}
