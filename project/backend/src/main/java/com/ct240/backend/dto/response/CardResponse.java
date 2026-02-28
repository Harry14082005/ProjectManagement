package com.ct240.backend.dto.response;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardResponse {
    String id;
    String boardId;
    String name;
    Date createdAt;
}
