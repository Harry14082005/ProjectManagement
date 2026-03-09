package com.ct240.backend.controller;

import com.ct240.backend.dto.request.BoardUserRequest;
import com.ct240.backend.dto.response.ApiResponse;
import com.ct240.backend.dto.response.BoardUserResponse;
import com.ct240.backend.dto.response.UserResponse;
import com.ct240.backend.service.BoardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardUserController {
    @Autowired
    BoardUserService boardUserService;

    @PostMapping("boards/{boardId}/members")
    ApiResponse<BoardUserResponse> addMember(@PathVariable String boardId, @RequestBody BoardUserRequest request, Authentication authentication){
        ApiResponse<BoardUserResponse> apiResponse = new ApiResponse<>();

        var data = boardUserService.addMember(boardId, request, authentication);

        apiResponse.setData(data);

        return apiResponse;
    }

    @GetMapping("boards/{boardId}/members")
    ApiResponse<List<UserResponse>> getAllUsersInBoard(@PathVariable String boardId, Authentication authentication){
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();

        var data = boardUserService.getAllUsersInBoard(boardId, authentication);

        apiResponse.setData(data);

        return apiResponse;
    }
}
