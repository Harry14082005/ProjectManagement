package com.ct240.backend.dto.response;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceUserResponse {
    String userId;
    String spaceId;
    Role role;
}
