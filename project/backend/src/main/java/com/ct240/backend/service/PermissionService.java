package com.ct240.backend.service;


import com.ct240.backend.entity.BoardUser;
import com.ct240.backend.entity.Space;
import com.ct240.backend.entity.SpaceUser;
import com.ct240.backend.entity.User;
import com.ct240.backend.enums.Role;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.repository.*;
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

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardUserRepository boardUserRepository;

    public User getUserAuth(Authentication authentication){
        String username = authentication.getName();

        return userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
    }
    public boolean isMemberInSpace(String userId, String spaceId){
        return spaceUserRepository.existsByUserIdAndSpaceId(userId, spaceId);
    }

    public Role getRoleInSpace(String userId, String spaceId) {
        SpaceUser su = spaceUserRepository.findByUserIdAndSpaceId(userId, spaceId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXIST_IN_SPACE)
        );
        return su.getRole();
    }

    public Role getRoleInSpaceByBoardId(String userId, String boardId){
        Space space = boardRepository.findSpaceByBoardId(boardId);

        return getRoleInSpace(userId, space.getId());
    }

    public boolean isOwnerOfBoard(String userId, String boarId){
        BoardUser bu = boardUserRepository.findByUserIdAndBoardId(userId, boarId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXIST_IN_BOARD)
        );

        return bu.isOwner();
    }

    public boolean isMemberInBoard(String userId, String boardId){
        return boardUserRepository.existsByUserIdAndBoardId(userId, boardId);
    }
}
