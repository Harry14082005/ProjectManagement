package com.ct240.backend.repository;

import com.ct240.backend.entity.SpaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, String> {
}
