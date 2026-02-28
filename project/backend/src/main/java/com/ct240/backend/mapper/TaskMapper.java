package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.TaskCreationRequest;
import com.ct240.backend.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toTask(TaskCreationRequest request);
}
