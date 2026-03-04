package com.ct240.backend.service;

import com.ct240.backend.dto.request.SpaceCreationRequest;
import com.ct240.backend.dto.request.SpaceUpdateRequest;
import com.ct240.backend.dto.response.SpaceResponse;
import com.ct240.backend.entity.Space;
import com.ct240.backend.entity.SpaceUser;
import com.ct240.backend.entity.SpaceUserId;
import com.ct240.backend.entity.User;
import com.ct240.backend.enums.Role;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.mapper.SpaceMapper;
import com.ct240.backend.repository.SpaceRepository;
import com.ct240.backend.repository.SpaceUserRepository;
import com.ct240.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpaceService {
    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    @Autowired
    SpaceMapper spaceMapper;


//    public SpaceResponse getAllSpaces(String userId){
//        SpaceResponse spaceResponse = new SpaceResponse();
//
//    }
    public SpaceResponse createSpace(SpaceCreationRequest request, Authentication authentication){
        SpaceResponse spaceResponse = new SpaceResponse();

        //lấy người dùng
        String username = authentication.getName();

        //nếu người dùng hông có thì chạy ngoại lệ
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        //tạo space trước
        Space space = spaceMapper.toSpace(request);
        space.setCreateAt(new Date());

        spaceRepository.save(space);


        //tạo SpaceUserId
        SpaceUserId spaceUserId = new SpaceUserId();
        spaceUserId.setSpaceId(space.getId());
        spaceUserId.setUserId(user.getId());

        //tạo SpaceUser
        SpaceUser spaceUser = new SpaceUser();
        spaceUser.setId(spaceUserId);
        spaceUser.setSpace(space);
        spaceUser.setUser(user);
        spaceUser.setRole(Role.OWNER);

        spaceUserRepository.save(spaceUser);

        //gán response
        return SpaceResponse.builder()
                .id(space.getId())
                .name(space.getName())
                .description(space.getDescription())
                .createAt(space.getCreateAt())
                .build();
    }
    public SpaceResponse getSpace(String spaceId, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () ->new AppException(ErrorCode.USER_NOT_FOUND));

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);

        if (!isMember){
            throw  new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Space space = spaceRepository.findById(spaceId).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND));

        return spaceMapper.toSpaceResponse(space);
    }

    public List<SpaceResponse> getAllSpaces(Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        var spaceList = spaceRepository.findAllByUserId(user.getId());

        return spaceList.stream()
                .map(space -> spaceMapper.toSpaceResponse(space))
                .collect(Collectors.toList());
    }

    public SpaceResponse updateSpace(String spaceId, SpaceUpdateRequest request, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        /// Chỉ có quyền OWNER mới được SỬA thông tin Space ///

        Space space = spaceRepository.findById(spaceId).orElseThrow(
                () -> new AppException(ErrorCode.SPACE_NOT_FOUND)
        );

        boolean isOwner = spaceUserRepository
                .existsByUserIdAndSpaceIdAndRole(user.getId(), spaceId, Role.OWNER);

        if(!isOwner){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        spaceMapper.updateSpace(space, request);

        spaceRepository.save(space);

        return spaceMapper.toSpaceResponse(space);

    }

    public void deleteSpace(String spaceId, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        /// Chỉ có quyền OWNER mới được XOÁ thông tin Space ///

        Space space = spaceRepository.findById(spaceId).orElseThrow(
                () -> new AppException(ErrorCode.SPACE_NOT_FOUND)
        );

        boolean isOwner = spaceUserRepository
                .existsByUserIdAndSpaceIdAndRole(user.getId(), spaceId, Role.OWNER);

        if(!isOwner){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        ///thiết lập xoá cascade cho Entity Space
        ///             để khi xoá space tự động xoá trong bảng SpaceUser///
        spaceRepository.delete(space);
    }

}
