package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.BoardCreationRequest;
import com.ct240.backend.dto.request.BoardUpdateRequest;
import com.ct240.backend.dto.response.BoardResponse;
import com.ct240.backend.entity.Board;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArray;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.crypto.spec.PSource;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board toBoard(BoardCreationRequest request);
    //spaceID : String, phai dung @Mapping de covert qua
    @Mapping(source = "space.id", target = "spaceId")

    BoardResponse toBoardResponse (Board board);
    void updateBoard(@MappingTarget Board board, BoardUpdateRequest request);


}
