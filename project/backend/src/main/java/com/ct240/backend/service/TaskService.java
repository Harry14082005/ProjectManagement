package com.ct240.backend.service;

import com.ct240.backend.dto.request.TaskCreationRequest;
import com.ct240.backend.dto.request.TaskUpdateRequest;
import com.ct240.backend.dto.response.TaskResponse;
import com.ct240.backend.entity.Card;
import com.ct240.backend.entity.Task;
import com.ct240.backend.entity.User;
import com.ct240.backend.exception.ErrorCode;
import com.ct240.backend.mapper.TaskMapper;
import com.ct240.backend.repository.CardRepository;
import com.ct240.backend.repository.SpaceUserRepository;
import com.ct240.backend.repository.TaskRepository;
import com.ct240.backend.repository.UserRepository;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    @Autowired
    TaskMapper taskMapper;

    public TaskResponse createTask (String cardId, TaskCreationRequest request, Authentication authentication){
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
        //Tao task
        Task task = taskMapper.toTask(request);
        task.setCard(card);

        taskRepository.save(task);

        return taskMapper.toTaskResponse(task);

    }

    public List<TaskResponse> getAllTasks(String cardId, Authentication authentication) {
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
        var taskList = taskRepository.findByCardId(cardId);

        return taskList.stream()
                .map(task -> taskMapper.toTaskResponse(task))
                .collect(Collectors.toList());
    }

    public TaskResponse updateTask (String taskId, TaskUpdateRequest request,  Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_NOT_FOUND)
        );

        String spaceId = task.getCard().getBoard().getSpace().getId();

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);
        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        // update task
        taskMapper.updateTask(task, request);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    public void deleteTask(String taskId, Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new AppException(ErrorCode.TASK_NOT_FOUND)
        );

        String spaceId = task.getCard().getBoard().getSpace().getId();

        boolean isMember = spaceUserRepository.existsByUserIdAndSpaceId(user.getId(), spaceId);
        if (!isMember){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        taskRepository.delete(task);
    }
}
