package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.TaskCreationRequest;
import com.ct240.backend.dto.request.TaskUpdateRequest;
import com.ct240.backend.dto.response.TaskResponse;
import com.ct240.backend.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toTask(TaskCreationRequest request);
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "completed", target = "isCompleted")
    TaskResponse toTaskResponse (Task task);

    void updateTask (@MappingTarget Task task, TaskUpdateRequest request);
}
