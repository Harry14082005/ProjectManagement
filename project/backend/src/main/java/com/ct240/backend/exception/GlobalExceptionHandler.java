package com.ct240.backend.exception;

import com.ct240.backend.dto.response.ApiResponse;
import com.ct240.backend.service.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException ex) {

        ApiResponse<Object> response = new ApiResponse<>();
        response.setCode(ex.getErrorCode().getCode());
        response.setMessage(ex.getErrorCode().getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409 chuẩn REST cho duplicate
                .body(response);
    }
}