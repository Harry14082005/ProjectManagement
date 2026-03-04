package com.ct240.backend.dto.request;

import com.ct240.backend.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
