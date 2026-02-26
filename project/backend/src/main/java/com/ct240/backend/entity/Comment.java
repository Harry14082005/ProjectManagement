package com.ct240.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE )
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String content;
    Date createAt;

    @ManyToOne
    @JoinColumn(name = "taskId")
    Task task;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
