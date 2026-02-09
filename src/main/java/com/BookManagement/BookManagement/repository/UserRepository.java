package com.BookManagement.BookManagement.repository;

import com.BookManagement.BookManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    SELECT COUNT(*) FROM users WHERE username = ?
//    boolean existsByUsernameAndCity(String username, String city);
    boolean existsByUsername(String username);

    //login
    User findByUsername(String username);
//    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
