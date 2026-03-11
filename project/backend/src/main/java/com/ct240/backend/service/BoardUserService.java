package com.ct240.backend.service;

import com.ct240.backend.dto.request.BoardUserRequest;
import com.ct240.backend.dto.response.BoardUserResponse;
import com.ct240.backend.dto.response.UserResponse;
import com.ct240.backend.entity.Board;
import com.ct240.backend.entity.BoardUser;
import com.ct240.backend.entity.BoardUserId;
import com.ct240.backend.entity.User;
import com.ct240.backend.enums.Role;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.mapper.BoardUserMapper;
import com.ct240.backend.mapper.UserMapper;
import com.ct240.backend.repository.BoardRepository;
import com.ct240.backend.repository.BoardUserRepository;
import com.ct240.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardUserService {
    @Autowired
    BoardUserRepository boardUserRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionService permissionService;

    @Autowired
    BoardUserMapper boardUserMapper;
    @Autowired
    UserMapper userMapper;

    ///         NGƯỜI CÓ QUYỀN THÊM MEMBER VÀO BOARD       ///
    /// 1. OWNER, ADMIN của SPACE                          ///
    /// 2. OWNER của BOARD                                 ///
    /// 3. MEMBER của SPACE nếu BOARD có isPrivate = FALSE ///
    /// *. Người được thêm chỉ là MEMBER của SPACE         ///
    public BoardUserResponse addMember(String boardId, BoardUserRequest request, Authentication authentication){
        User currentUser = permissionService.getUserAuth(authentication);

        Board board = permissionService.getBoard(boardId);

        User addedUser = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        permissionService.requireAddBoardMember(currentUser.getId(), boardId);
        //nếu người đó trong board rồi thì cũng hông được
        boolean isMember = permissionService.isMemberInBoard(request.getUserId(), boardId);
        if(isMember){
            throw new AppException(ErrorCode.USER_EXISTED_IN_BOARD);
        }

        //khởi tạo ID
        BoardUserId boardUserId = new BoardUserId();
        boardUserId.setUserId(addedUser.getId());
        boardUserId.setBoardId(boardId);

        //lưu
        BoardUser boardUser = new BoardUser();
        boardUser.setId(boardUserId);
        boardUser.setUser(addedUser);
        boardUser.setBoard(board);
        boardUser.setOwner(false);  // - CHECK * -  KHÔNG KHÔNG ĐƯỢC THAY ĐỔI

        boardUserRepository.save(boardUser);

        return boardUserMapper.toSpaceUserResponse(boardUser);
    }

    public List<UserResponse> getAllUsersInBoard(String boardId, Authentication authentication){
        User user = permissionService.getUserAuth(authentication);

        //kiểm tra phải thành viên board hông
        boolean isMember = permissionService.isMemberInBoard(user.getId(), boardId);
        if(!isMember){
            throw new AppException(ErrorCode.USER_NOT_EXIST_IN_BOARD);
        }

        var listUsers = boardUserRepository.findUsersByBoardId(boardId);

        return listUsers.stream()
                .map(u -> userMapper.toUserResponse(u))
                .collect(Collectors.toList());
    }
}
