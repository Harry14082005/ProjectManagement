package com.ct240.backend.dto.response;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String id;
    String taskId;
    String userId;
    String content;
    Date createdAt;
    UserResponse user;
}
