package com.ct240.backend.controller;

import com.ct240.backend.dto.request.SpaceCreationRequest;
import com.ct240.backend.dto.response.ApiResponse;
import com.ct240.backend.dto.response.SpaceResponse;
import com.ct240.backend.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spaces")
public class SpaceController {
    @Autowired
    SpaceService spaceService;

    @PostMapping
    ApiResponse<SpaceResponse> createSpace(@RequestBody SpaceCreationRequest request, Authentication authentication){
        ApiResponse<SpaceResponse> apiResponse = new ApiResponse<>();

        apiResponse.setData(spaceService.createSpace(request, authentication));

        return apiResponse;
    }
    @GetMapping("/{spaceId}")
    ApiResponse<SpaceResponse> getSpace(@PathVariable String spaceId, Authentication authentication){
        ApiResponse<SpaceResponse> apiResponse = new ApiResponse<>();

        SpaceResponse response = spaceService.getSpace(spaceId, authentication);

        apiResponse.setData(response);

        return apiResponse;
    }
}
