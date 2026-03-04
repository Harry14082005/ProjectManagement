package com.ct240.backend.repository;

import com.ct240.backend.entity.SpaceUser;
import com.ct240.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, String> {

    boolean existsByUserIdAndSpaceId(String userId, String spaceId);
}
