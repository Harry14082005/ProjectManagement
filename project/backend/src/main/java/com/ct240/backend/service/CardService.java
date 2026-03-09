package com.ct240.backend.service;

import com.ct240.backend.dto.request.CardCreationRequest;
import com.ct240.backend.dto.request.CardUpdateRequest;
import com.ct240.backend.dto.response.CardResponse;
import com.ct240.backend.entity.Board;
import com.ct240.backend.entity.Card;
import com.ct240.backend.entity.User;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.mapper.CardMapper;
import com.ct240.backend.repository.*;
import jdk.dynalink.beans.MissingMemberHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardUserRepository boardUserRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    @Autowired
    CardMapper cardMapper;

    public CardResponse createCard(String boardId, CardCreationRequest request, Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new AppException(ErrorCode.BOARD_NOT_FOUND)
        );
        String spaceId = board.getSpace().getId();

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);

        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        //tao card
        Card card = cardMapper.toCard(request);
        card.setBoard(board);
        card.setCreateAt(new Date());

        cardRepository.save(card);

        return cardMapper.toCardResponse(card);
    }

    public List<CardResponse> getAllCards(String boardId, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                () ->new AppException(ErrorCode.BOARD_NOT_FOUND)
        );

        String spaceId = board.getSpace().getId();

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);
        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var cardList = cardRepository.findByBoardId(boardId);

        return cardList.stream()
                .map(card -> cardMapper.toCardResponse(card))
                .collect(Collectors.toList());
    }

    public CardResponse updateCard(String cardId, CardUpdateRequest request, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new AppException(ErrorCode.CARD_NOT_FOUND)
        );

        String spaceId = card.getBoard().getSpace().getId();

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);
        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        cardMapper.updateCard(card, request);
        cardRepository.save(card);
        return cardMapper.toCardResponse(card);
    }
    public void deleteCard (String cardId, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new AppException(ErrorCode.CARD_NOT_FOUND)
        );
        String spaceId = card.getBoard().getSpace().getId();

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);
        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        cardRepository.delete(card);


    }




}//