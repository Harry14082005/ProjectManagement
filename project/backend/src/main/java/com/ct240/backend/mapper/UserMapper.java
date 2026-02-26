package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.UserCreationRequest;
import com.ct240.backend.dto.response.UserResponse;
import com.ct240.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
}
