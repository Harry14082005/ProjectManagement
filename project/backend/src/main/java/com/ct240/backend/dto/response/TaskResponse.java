package com.ct240.backend.dto.response;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {
    String id;
    String cardId;
    String name;
    String description;
    boolean isCompleted;
    Date deadline;
    Date createdAt;

}
