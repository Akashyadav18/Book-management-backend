package com.BookManagement.BookManagement.controller;

import com.BookManagement.BookManagement.apiResponse.ApiResponse;
import com.BookManagement.BookManagement.entity.User;
import com.BookManagement.BookManagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class    UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody @Valid User user) {
        try {
            User saveUser = service.register(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("User Registered Successfully", saveUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Fail to Register User", null));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody @Valid User user, HttpSession session) {
        try {
            User dbUser = service.login(user.getUsername(), user.getPassword());
            if (dbUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Invalid Credentials / Register First", null));
            }

            session.setAttribute("USER_ID", dbUser.getId());
            session.setAttribute("USERNAME", dbUser.getUsername());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("Login Successful", dbUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Login Failed", null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpSession session) {
        try {
            session.invalidate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("Logout Successful", "Done"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Logout Failed", null));
        }
    }
}
