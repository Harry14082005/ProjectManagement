package com.ct240.backend.service;

import com.ct240.backend.dto.response.SpaceResponse;
import com.ct240.backend.repository.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceService {
    @Autowired
    SpaceRepository spaceRepository;

//    public SpaceResponse getAllSpaces(String userId){
//        SpaceResponse spaceResponse = new SpaceResponse();
//
//    }

}
