package com.ct240.backend.controller;

import com.ct240.backend.dto.request.SpaceCreationRequest;
import com.ct240.backend.dto.response.ApiResponse;
import com.ct240.backend.dto.response.SpaceResponse;
import com.ct240.backend.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
