package com.ct240.backend.dto.response;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskAssignmentResponse {
    String taskId;
    String userId;
    UserResponse user;
}
