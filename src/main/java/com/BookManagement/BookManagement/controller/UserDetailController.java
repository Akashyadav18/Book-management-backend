package com.BookManagement.BookManagement.controller;

import com.BookManagement.BookManagement.apiResponse.ApiResponse;
import com.BookManagement.BookManagement.entity.UserDetails;
import com.BookManagement.BookManagement.service.UserDetailService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userDetail")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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

    @GetMapping("/getUserDetail")
    public ResponseEntity<?> getAllUserDetails(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("USER_ID");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Please login first", null));
            }
            UserDetails userDetails = userDetailService.getAllUserDetails(userId);
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>("No user details found", null));
            }
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Fail to fetch data", null));
        }
    }

    @DeleteMapping("/deleteUserDetail/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        try{
             userDetailService.deleteUserDetail(id);
             return ResponseEntity.status(HttpStatus.OK)
                     .body(new ApiResponse("User deleted SUccessfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Fail to delete user", null));
        }
    }

    @PutMapping("/updateUserDetail/{id}")
    public ResponseEntity<ApiResponse<UserDetails>> updateUserDetail(@PathVariable Long id, @RequestBody UserDetails userDetails){
        try{
            UserDetails updateUser = userDetailService.updateUserDetail(id, userDetails);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("User updated successfully", updateUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Fail to update user", null));
        }
    }
}
