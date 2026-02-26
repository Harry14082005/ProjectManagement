package com.ct240.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_EXISTED(1100, "User Existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1101, "User Not Found", HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
