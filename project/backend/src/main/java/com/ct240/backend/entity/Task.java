package com.ct240.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE )
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String description;
    boolean isCompleted;
    Date deadline;
    Date createAt;

    @ManyToOne
    @JoinColumn(name = "cardId")
    Card card;

    @OneToMany(mappedBy = "task")
    List<TaskAssignment> taskAssignmentList;
}
