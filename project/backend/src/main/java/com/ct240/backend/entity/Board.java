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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String description;
    Date createAt;
    boolean isPrivate;

    //liên kết tới tới bảng Space
    @ManyToOne
    @JoinColumn(name = "spaceId")
    Space space;

    @OneToMany(mappedBy = "board")
    List<Card> cards;

}