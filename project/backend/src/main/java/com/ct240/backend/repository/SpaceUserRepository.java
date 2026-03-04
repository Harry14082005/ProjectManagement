package com.ct240.backend.repository;

import com.ct240.backend.entity.Space;
import com.ct240.backend.entity.SpaceUser;
import com.ct240.backend.entity.User;
import com.ct240.backend.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, String> {

    boolean existsByUserIdAndSpaceId(String userId, String spaceId);
    boolean existsByUserIdAndSpaceIdAndRole(String userId, String spaceId, Role role);
}
