package com.ct240.backend.repository;

import com.ct240.backend.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, String> {
    List <Card> findByBoardId(String boardId);

}
