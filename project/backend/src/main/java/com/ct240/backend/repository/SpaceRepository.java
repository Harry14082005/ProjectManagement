package com.ct240.backend.repository;

import com.ct240.backend.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, String> {

    List<Space> findAllById(String id);
}
