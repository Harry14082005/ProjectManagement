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
    UNAUTHORIZED(1004, "Unauthorized", HttpStatus.FORBIDDEN),

    USER_EXISTED(1100, "User Existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1101, "User Not Found", HttpStatus.NOT_FOUND),

    SPACE_NOT_FOUND(1201, "Space Not Found", HttpStatus.NOT_FOUND),
    USER_EXISTED_IN_SPACE(1202, "User Existed In The Space", HttpStatus.CONFLICT),
    USER_NOT_EXIST_IN_SPACE(1203, "User Not Exist In The Space" , HttpStatus.NOT_FOUND),
    OWNER_CANNOT_LEAVE_SPACE(1204, "Owner Cannot Leave The Space", HttpStatus.FORBIDDEN),

    BOARD_NOT_FOUND(1301, "Board Not Found", HttpStatus.NOT_FOUND),
    USER_EXISTED_IN_BOARD(1302, "User Existed In The Board", HttpStatus.CONFLICT),
    USER_NOT_EXIST_IN_BOARD(1303, "User Not Exist In The Board" , HttpStatus.NOT_FOUND),
    OWNER_CANNOT_LEAVE_BOARD(1304, "Owner Cannot Leave The Board", HttpStatus.FORBIDDEN),

    CARD_NOT_FOUND(1401, "Card Not Found", HttpStatus.NOT_FOUND),


    TASK_NOT_FOUND(1501, "Task Not Found", HttpStatus.NOT_FOUND),
    USER_NOT_ASSIGNED_TO_TASK(1503, "User Not Assigned To The Task", HttpStatus.NOT_FOUND),

    COMMENT_NOT_FOUND(1601, "Comment Not Found", HttpStatus.NOT_FOUND),

    NOTIFICATION_NOT_FOUND(1701,  "Notification Not Found", HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
