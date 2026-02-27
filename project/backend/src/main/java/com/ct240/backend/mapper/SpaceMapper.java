package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.SpaceCreationRequest;
import com.ct240.backend.entity.Space;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpaceMapper {
    Space toSpace(SpaceCreationRequest request);
}
