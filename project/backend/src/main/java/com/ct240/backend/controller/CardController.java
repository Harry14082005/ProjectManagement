package com.ct240.backend.controller;

import com.ct240.backend.dto.request.CardCreationRequest;
import com.ct240.backend.dto.request.CardUpdateRequest;
import com.ct240.backend.dto.response.ApiResponse;
import com.ct240.backend.dto.response.CardResponse;
import com.ct240.backend.service.CardService;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping ("/boards/{boardId}/cards")
    ApiResponse<CardResponse> createCard(
            @PathVariable String boardId,
            @RequestBody CardCreationRequest request,
            Authentication authentication){
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();

        apiResponse.setData(cardService.createCard(boardId, request, authentication));
        return apiResponse;
    }
    @GetMapping ("/boards/{boardId}/cards")
    ApiResponse<List<CardResponse>> getAllCards(
            @PathVariable String boardId,
            Authentication authentication){
        ApiResponse<List<CardResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setData(cardService.getAllCards(boardId, authentication));
        return apiResponse;
    }
    @PutMapping ("/cards/{cardId}")
    ApiResponse<CardResponse> updateCard(
            @PathVariable String cardId,
            @RequestBody CardUpdateRequest request,
            Authentication authentication){
        ApiResponse<CardResponse> apiResponse = new ApiResponse<>();

        apiResponse.setData(cardService.updateCard(cardId, request, authentication));

        return apiResponse;
    }

    @DeleteMapping ("/cards/{cardId}")
    ApiResponse<Void> deleteCard(@PathVariable String cardId, Authentication authentication){
        ApiResponse<Void> apiResponse = new ApiResponse();

        cardService.deleteCard(cardId, authentication);
        apiResponse.setMessage("Card and its Tasks are deleted successfully");

        return apiResponse;
    }

}
