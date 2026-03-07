package com.ct240.backend.repository;

import com.ct240.backend.entity.Task;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    List <Task> findByCardId (String cardId);
}
