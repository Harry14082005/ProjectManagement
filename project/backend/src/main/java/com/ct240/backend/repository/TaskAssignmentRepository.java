package com.ct240.backend.repository;

import com.ct240.backend.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, String> {
}
