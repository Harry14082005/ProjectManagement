package com.ct240.backend.repository;

import com.ct240.backend.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser, String> {
}
