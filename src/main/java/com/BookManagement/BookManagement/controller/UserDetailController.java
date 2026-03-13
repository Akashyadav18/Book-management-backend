package com.BookManagement.BookManagement.controller;

import com.BookManagement.BookManagement.apiResponse.ApiResponse;
import com.BookManagement.BookManagement.entity.UserDetails;
import com.BookManagement.BookManagement.service.UserDetailService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userDetail")
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse<UserDetails>> saveUserDetail(@RequestBody @Valid UserDetails userDetails, HttpSession session) {
        try{
            Long userId = (Long) session.getAttribute("USER_ID");

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Please login first", null));
            }
            UserDetails saveUser = userDetailService.createUserDetail(userId, userDetails);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("User Detail Created Successfully", saveUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Fail to create user Detail", null));
        }
    }
}
