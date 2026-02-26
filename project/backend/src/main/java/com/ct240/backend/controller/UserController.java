package com.ct240.backend.controller;

import com.ct240.backend.dto.request.UserCreationRequest;
import com.ct240.backend.dto.response.ApiResponse;
import com.ct240.backend.dto.response.UserResponse;
import com.ct240.backend.entity.User;
import com.ct240.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    ApiResponse <List<UserResponse>> getAllUsers(){
        ApiResponse <List<UserResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setData(userService.getAllUsers());

        return apiResponse;
    }


    /// tạo người dùng đem qua AuthService ///
//    @PostMapping
//    ApiResponse <UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
//        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
//
//        apiResponse.setData(userService.createUser(request));
//
//        return apiResponse;
//    }



}
