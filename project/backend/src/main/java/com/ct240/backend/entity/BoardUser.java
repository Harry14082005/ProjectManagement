package com.ct240.backend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @JoinColumn (name = "userId")
    User user;

    @ManyToOne
    @JoinColumn(name = "boadId")
    Board board;
}
