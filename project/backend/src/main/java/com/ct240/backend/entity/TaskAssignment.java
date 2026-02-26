package com.ct240.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE )
public class TaskAssignment {

    @EmbeddedId
    TaskAssignmentId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "taskId")
    Task task;

}
