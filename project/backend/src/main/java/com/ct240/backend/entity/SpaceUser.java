package com.ct240.backend.entity;

import com.ct240.backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE )
public class SpaceUser {
    @EmbeddedId
    SpaceUserId id;

    Role role;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @MapsId("spaceId")
    @JoinColumn(name = "spaceId")
    Space space;
}
