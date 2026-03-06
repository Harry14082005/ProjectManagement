package com.ct240.backend.repository;

import com.ct240.backend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, String> {
    List<Board> findBySpaceId(String spaceId);
    List<Board> findBySpaceIdAndIsPrivateFalse(String spaceId);
}
