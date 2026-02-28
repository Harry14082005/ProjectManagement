package com.ct240.backend.dto.request;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpaceUserUpdateRequest {
    String id;
    Role role;
}
