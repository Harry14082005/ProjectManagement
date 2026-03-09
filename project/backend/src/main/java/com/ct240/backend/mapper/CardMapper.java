package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.CardCreationRequest;
import com.ct240.backend.dto.request.CardUpdateRequest;
import com.ct240.backend.dto.response.CardResponse;
import com.ct240.backend.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toCard(CardCreationRequest request);
    @Mapping(source = "board.id", target ="boardId")
    CardResponse toCardResponse (Card card);
    void updateCard (@MappingTarget Card card, CardUpdateRequest request);
}
