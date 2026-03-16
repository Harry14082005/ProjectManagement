package com.ct240.backend.mapper;

import com.ct240.backend.dto.response.NotificationResponse;
import com.ct240.backend.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface NotificationMapper {
    NotificationResponse toResponse(Notification notification);
}
