package com.ct240.backend.service;


import com.ct240.backend.entity.Space;
import com.ct240.backend.entity.SpaceUser;
import com.ct240.backend.entity.User;
import com.ct240.backend.enums.Role;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.repository.SpaceRepository;
import com.ct240.backend.repository.SpaceUserRepository;
import com.ct240.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    public boolean isMemberInSpace(String userId, String spaceId){
        return spaceUserRepository.existsByUserIdAndSpaceId(userId, spaceId);
    }

    public Role getRoleInSpace(String userId, String spaceId) {
        SpaceUser su = spaceUserRepository.findByUserIdAndSpaceId(userId, spaceId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXIST_IN_SPACE)
        );
        return su.getRole();
    }
}
