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
public class BoardUser {
    @EmbeddedId
    BoardUserId id;

    boolean isOwner;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn (name = "userId")
    User user;

    @ManyToOne
    @MapsId("boardId")
    @JoinColumn(name = "boardId")
    Board board;
}
