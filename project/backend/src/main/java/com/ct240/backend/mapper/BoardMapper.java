package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.BoardCreationRequest;
import com.ct240.backend.entity.Board;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board toBoard(BoardCreationRequest request);
}
