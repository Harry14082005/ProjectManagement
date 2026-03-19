package com.ct240.backend.service;

import com.ct240.backend.dto.request.ChangePasswordRequest;
import com.ct240.backend.dto.request.UserCreationRequest;
import com.ct240.backend.dto.request.UserUpdateRequest;
import com.ct240.backend.dto.response.UserResponse;
import com.ct240.backend.entity.User;
import com.ct240.backend.exception.AppException;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.mapper.UserMapper;
import com.ct240.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionService permissionService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse getUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UserUpdateRequest request, Authentication authentication){
        User user = permissionService.getUserAuth(authentication);

        userMapper.updateUser(user, request);

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .avatarURL(user.getAvatarURL())
                .build();
    }

    public List<UserResponse> searchUsers(String keyword){
        var listUsers = userRepository.findByUsernameContaining(keyword);

        return listUsers.stream()
                .map(user -> userMapper.toUserResponse(user))
                .collect(Collectors.toList());
    }

    public UserResponse getUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        return userMapper.toUserResponse(user);
    }

    public void updatePassword(ChangePasswordRequest request, Authentication authentication){
        User user = permissionService.getUserAuth(authentication);

        //kiểm tra mật khẩu đang nhập có đúng hay không thì mới đổi được
        String currentPassword = request.getCurrentPassword();

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

}
