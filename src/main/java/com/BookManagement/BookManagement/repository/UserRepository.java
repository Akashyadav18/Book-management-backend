package com.BookManagement.BookManagement.repository;

import com.BookManagement.BookManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
//    SELECT COUNT(*) FROM users WHERE username = ?
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
