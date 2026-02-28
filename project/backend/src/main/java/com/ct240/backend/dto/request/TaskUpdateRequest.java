package com.ct240.backend.dto.request;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskUpdateRequest {
    String name;
    String description;
    boolean isCompleted;
    Date deadLine;
}
