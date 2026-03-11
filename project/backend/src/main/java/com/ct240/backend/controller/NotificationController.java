package com.ct240.backend.controller;

import com.ct240.backend.dto.response.ApiResponse;
import com.ct240.backend.dto.response.NotificationResponse;
import com.ct240.backend.entity.Notification;
import com.ct240.backend.service.NotificationService;
import com.ct240.backend.service.SseEmitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    SseEmitterService sseEmitterService;

    @Autowired
    NotificationService notificationService;

    // Frontend gọi endpoint này để "đăng ký" nhận thông báo
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(Authentication authentication) {
        return sseEmitterService.createEmitter(authentication);
    }

    // Lấy danh sách notification cũ khi mở app
    @GetMapping
    public ApiResponse<List<NotificationResponse>> getAllNotifications(Authentication authentication) {
        ApiResponse<List<NotificationResponse>> apiResponse = new ApiResponse<>();

        var data = notificationService.getAllNotifications(authentication);

        apiResponse.setData(data);

        return apiResponse;
    }
}
