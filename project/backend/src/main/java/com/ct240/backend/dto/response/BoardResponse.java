package com.ct240.backend.dto.response;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardResponse {
    String id;
    String spaceId;
    String name;
    String description;
    boolean isPrivate;
    Date createAt;
}
