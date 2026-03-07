package com.ct240.backend.service;

import com.ct240.backend.dto.request.BoardCreationRequest;
import com.ct240.backend.dto.request.BoardUpdateRequest;
import com.ct240.backend.dto.response.BoardResponse;
import com.ct240.backend.entity.*;
import com.ct240.backend.enums.Role;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.mapper.BoardMapper;
import com.ct240.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardUserRepository boardUserRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    @Autowired
    BoardMapper boardMapper;

    public BoardResponse createBoard(String spaceId, BoardCreationRequest request, Authentication authentication){

        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND));

        Space space = spaceRepository.findById(spaceId).orElseThrow(
                () -> new AppException(ErrorCode.SPACE_NOT_FOUND));

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);
        if (!isMember)
        {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Board board = boardMapper.toBoard(request);
        board.setSpace(space);// relationship to space, that's why add this code
        board.setCreateAt(new Date());
        boardRepository.save(board);

        //tao BoardUserId
        BoardUserId boardUserId = new BoardUserId();
        boardUserId.setBoardId(board.getId());
        boardUserId.setUserId(user.getId());

        //tao BoardUser
        BoardUser boardUser = new BoardUser();
        boardUser.setId(boardUserId);
        boardUser.setBoard(board);
        boardUser.setUser(user);
        boardUser.setOwner(true);

        boardUserRepository.save(boardUser);

        return boardMapper.toBoardResponse(board);
    }
    public BoardResponse getBoard (String boardId, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        boolean isMember = boardUserRepository.existsByUserIdAndBoardId(user.getId(), boardId);

        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new AppException(ErrorCode.BOARD_NOT_FOUND)
        );
        // xuly private
        if(board.isPrivate()){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return boardMapper.toBoardResponse(board);
        // muon dung Mapper o day phai code ben BoardMapper
    }

    public List<BoardResponse> getAllBoards(String spaceId, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);
        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        // xuly private
        var boardList = boardRepository.findBySpaceIdAndIsPrivateFalse(spaceId);

        return boardList.stream()
                .map(board -> boardMapper.toBoardResponse(board))
                .collect(Collectors.toList());
    }

    public BoardResponse updateBoard( String boardId, BoardUpdateRequest request, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        //tim board
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new AppException(ErrorCode.BOARD_NOT_FOUND)
        );
        //Lay spaceId tu board
        String spaceId = board.getSpace().getId();

        //Kiem duyet owner
        boolean isOwner = spaceUserRepository
                .existsByUserIdAndSpaceIdAndRole(user.getId(), spaceId, Role.OWNER);

        if (!isOwner){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        boardMapper.updateBoard(board, request);
        boardRepository.save(board);
        return boardMapper.toBoardResponse(board);

    }

        public void deleteBoard (String boardId, Authentication authentication){
            String username = authentication.getName();

            User user = userRepository.findByUsername(username).orElseThrow(
                    ()-> new AppException(ErrorCode.USER_NOT_FOUND)
            );

            Board board = boardRepository.findById(boardId).orElseThrow(
                    () -> new AppException(ErrorCode.BOARD_NOT_FOUND)
            );
            boolean isOwner = boardUserRepository.existsByUserIdAndBoardIdAndIsOwner(user.getId(), boardId, Role.OWNER);
            if (!isOwner){
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
            boardRepository.delete(board);
        }

}
