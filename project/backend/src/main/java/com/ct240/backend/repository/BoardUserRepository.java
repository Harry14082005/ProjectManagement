package com.ct240.backend.repository;

import com.ct240.backend.entity.BoardUser;
import com.ct240.backend.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser, String> {
    boolean existsByUserIdAndBoardId (String userId, String boardId);
//    boolean exitsByUserIdAndSpaceId (String userId, String spaceId);
    boolean existsByUserIdAndBoardIdAndIsOwner(String userId, String boardId, Role role);

}
