package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.SpaceCreationRequest;
import com.ct240.backend.dto.request.SpaceUpdateRequest;
import com.ct240.backend.dto.response.SpaceResponse;
import com.ct240.backend.entity.Space;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpaceMapper {
    Space toSpace(SpaceCreationRequest request);
    void updateSpace(@MappingTarget  Space space, SpaceUpdateRequest request);

    SpaceResponse toSpaceResponse(Space space);
}
