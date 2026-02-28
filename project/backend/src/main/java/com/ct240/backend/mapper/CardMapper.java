package com.ct240.backend.mapper;

import com.ct240.backend.dto.request.CardCreationRequest;
import com.ct240.backend.entity.Card;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toCard(CardCreationRequest request);
}
