# CT240 Backend API Documentation

API backend cho hệ thống phân công công việc. Base URL: `http://localhost:8080//api`

Tất cả các request (trừ `/auth/**`) đều yêu cầu header:
```
Authorization: Bearer <token>
```

---

## Mục lục

- [Auth](#auth)
- [User](#user)
- [Space](#space)
- [SpaceUser](#spaceuser)
- [Board](#board)
- [BoardUser](#boarduser)
- [Card](#card)
- [Task](#task)
- [TaskAssignment](#taskassignment)
- [Comment](#comment)
- [Notification](#notification)
- [ErrorCode](#error-codes)

---

## Auth

### POST `/api/auth/register`

Đăng ký tài khoản mới.

**Request body — `UserCreationRequest`**
```json
{
  "username": "string",
  "password": "string",
  "name": "string",
  "avatarURL": "string (optional)"
}
```

**Response — `UserResponse`**
```json
{
  "id": "string (UUID)",
  "username": "string",
  "name": "string",
  "avatarURL": "string"
}
```

---

### POST `/api/auth/login`

Đăng nhập và lấy JWT token.

**Request body — `LoginRequest`**
```json
{
  "username": "string",
  "password": "string"
}
```

**Response — `AuthResponse`**
```json
{
  "token": "string (JWT)",
  "userId": "string"
}
```

---

## User

### GET `/api/users/profile`

Lấy thông tin người dùng hiện tại (dựa theo token).

**Response — `UserResponse`**
```json
{
  "id": "string",
  "username": "string",
  "name": "string",
  "avatarURL": "string"
}
```

---

### PUT `/api/users/update`

Cập nhật thông tin người dùng.

**Request body — `UserUpdateRequest`**
```json
{
  "name": "string (optional)",
  "avatarURL": "string (optional)",
  "password": "string (optional)"
}
```

### GET `/api/users/search`

Tìm kiếm người dùng với keyword `?keyword=`.

**Response — `List<UserResponse>`**
```json
[
    "user": {
      "id": "string",
      "username": "string",
      "name": "string",
      "avatarURL": "string"
    }
]
```

---

## Space

### POST `/api/spaces`

Tạo không gian làm việc mới. Người tạo sẽ tự động trở thành OWNER.

**Request body — `SpaceCreationRequest`**
```json
{
  "name": "string",
  "description": "string (optional)"
}
```

**Response — `SpaceResponse`**
```json
{
  "id": "string (UUID)",
  "name": "string",
  "description": "string",
  "createdAt": "2024-01-01T00:00:00Z"
}
```

---

### GET `/api/spaces/{spaceId}`

Lấy thông tin một Space.

**Response — `SpaceResponse`**
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "createdAt": "2024-01-01T00:00:00Z"
}
```

---

### GET `/api/spaces`

Lấy danh sách các Space mà người dùng hiện tại tham gia.

**Response — `List<SpaceResponse>`**
```json
[
  {
    "id": "string",
    "name": "string",
    "description": "string",
    "createdAt": "2024-01-01T00:00:00Z"
  }
]
```

---

### PUT `/api/spaces/{spaceId}`

Cập nhật thông tin Space. Yêu cầu quyền OWNER.

**Request body — `SpaceUpdateRequest`**
```json
{
  "name": "string (optional)",
  "description": "string (optional)"
}
```

**Response — `SpaceResponse`**

---

### DELETE `/api/spaces/{spaceId}`

Xóa Space. Yêu cầu quyền OWNER.

**Response**
```json
{
  "message": "Space deleted successfully"
}
```

---

## SpaceUser

### POST `/api/spaces/{spaceId}/members`

Thêm thành viên vào Space.

**Request body — `SpaceUserRequest`**
```json
{
  "userId": "string",
  "role": "ADMIN | MEMBER"
}
```

**Response — `SpaceUserResponse`**
```json
{
  "userId": "string",
  "spaceId": "string",
  "role": "OWNER | ADMIN | MEMBER"
}
```

---

### GET `/api/spaces/{spaceId}/members`

Lấy danh sách thành viên trong Space.

**Response — `List<SpaceMemberResponse>`**
```json
[
  {
    "userResponse": {
      "id": "string",
      "username": "string",
      "name": "string",
      "avatarURL": "string"
    }
    "role": "OWNER | ADMIN | MEMBER",
  }
]
```

---

### PUT `/api/spaces/{spaceId}/members/{userId}`

Cập nhật vai trò thành viên. Yêu cầu quyền OWNER.

**Request body — `SpaceUserUpdateRequest`**
```json
{
  "id": "string",
  "role": "ADMIN | MEMBER"
}
```

---

### DELETE `/api/spaces/{spaceId}/members/{userId}`

(Một người khác) Xóa thành viên khỏi Space. Yêu cầu quyền OWNER + ADMIN.

---

### DELETE `/api/spaces/{spaceId}/members`

Người dùng hiện tại rời khỏi Space. Yêu cầu OWNER không được quyền rời.

---

## Board

### POST `/api/spaces/{spaceId}/boards`

Tạo Board mới trong Space.

**Request body — `BoardCreationRequest`**
```json
{
  "name": "string",
  "description": "string (optional)",
  "isPrivate": false
}
```

**Response — `BoardResponse`**
```json
{
  "id": "string (UUID)",
  "spaceId": "string",
  "name": "string",
  "description": "string",
  "isPrivate": false,
  "createdAt": "2024-01-01T00:00:00Z"
}
```

---

### GET `/api/spaces/{spaceId}/boards`

Lấy danh sách Board trong Space.

**Response — `List<BoardResponse>`**

---

### GET `/api/boards/{boardId}`

Lấy thông tin một Board.

**Response — `BoardResponse`**

---

### PUT `/api/boards/{boardId}`

Cập nhật thông tin Board.

**Request body — `BoardUpdateRequest`**
```json
{
  "name": "string (optional)",
  "description": "string (optional)",
  "isPrivate": "boolean (optional)"
}
```

**Response — `BoardResponse`**

---

### DELETE `/api/boards/{boardId}`

Xóa Board.

---

## BoardUser

### POST `/api/boards/{boardId}/members`

Thêm thành viên vào Board.

**Request body — `BoardUserRequest`**
```json
{
  "userId": "string",
  "isOwner": false
}
```

**Response — `BoardUserResponse`**
```json
{
  "boardId": "string",
  "userId": "string",
  "isOwner": false
}
```

---

### GET `/api/boards/{boardId}/members`

Lấy danh sách thành viên Board.

**Response — `List<BoardUserResponse>`**

---

### DELETE `/api/boards/{boardId}/members/{userId}`

Xóa thành viên khỏi Board.

---

## Card

### POST `/api/boards/{boardId}/cards`

Tạo Card mới trong Board.

**Request body — `CardCreationRequest`**
```json
{
  "name": "string"
}
```

**Response — `CardResponse`**
```json
{
  "id": "string (UUID)",
  "boardId": "string",
  "name": "string",
  "createdAt": "2024-01-01T00:00:00Z"
}
```

---

### GET `/api/boards/{boardId}/cards`

Lấy danh sách Card trong Board.

**Response — `List<CardResponse>`**

---

### PUT `/api/cards/{cardId}`

Cập nhật tên Card.

**Request body — `CardUpdateRequest`**
```json
{
  "name": "string"
}
```

---

### DELETE `/api/cards/{cardId}`

Xóa Card và toàn bộ Task bên trong.

---

## Task

### POST `/api/cards/{cardId}/tasks`

Tạo Task mới trong Card.

**Request body — `TaskCreationRequest`**
```json
{
  "name": "string",
  "deadline": "2024-12-31T00:00:00Z (optional)"
  ...
}
```

**Response — `TaskResponse`**
```json
{
  "id": "string (UUID)",
  "cardId": "string",
  "name": "string",
  "isCompleted": false,
  "description": "string",
  "deadline": "2024-12-31T00:00:00Z",
  "createdAt": "2024-01-01T00:00:00Z"
}
```

---

### GET `/api/cards/{cardId}/tasks`

Lấy danh sách Task trong Card.

**Response — `List<TaskResponse>`**

---

### PUT `/api/tasks/{taskId}`

Cập nhật Task.

**Request body — `TaskUpdateRequest`**
```json
{
  "name": "string (optional)",
  "isCompleted": "boolean (optional)",
  "deadline": "date (optional)"
}
```

---

### DELETE `/api/task/{taskId}`

Xóa Task.

---

## TaskAssignment

### POST `/api/tasks/{taskId}/assign`

Phân công người thực hiện Task.

**Request body — `TaskAssignmentRequest`**
```json
{
  "userId": "string"
}
```

**Response — `TaskAssignmentResponse`**
```json
{
  "taskId": "string",
  "userId": "string",
  "user": {
    "id": "string",
    "username": "string",
    "name": "string",
    "avatarURL": "string"
  }
}
```

---

### GET `/api/tasks/{taskId}/assignments`

Lấy danh sách người được phân công cho Task.

**Response — `List<TaskAssignmentResponse>`**

---

### DELETE `/api/tasks/{taskId}/assign/{userId}`

Hủy phân công.

---

## Comment

### POST `/api/tasks/{taskId}/comments`

Thêm bình luận vào Task.

**Request body — `CommentCreationRequest`**
```json
{
  "content": "string"
}
```

**Response — `CommentResponse`**
```json
{
  "id": "string (UUID)",
  "taskId": "string",
  "userId": "string",
  "content": "string",
  "createdAt": "2024-01-01T00:00:00Z",
  "user": {
    "id": "string",
    "username": "string",
    "name": "string",
    "avatarURL": "string"
  }
}
```

---

### GET `/api/tasks/{taskId}/comments`

Lấy danh sách bình luận của Task.

**Response — `List<CommentResponse>`**

---

### DELETE `/api/comments/{commentId}`

Xóa bình luận. Chỉ người tạo mới được xóa.

---

## Notification
### GET `/api/notifications/subscribe`
Đăng ký nhận thông báo real-time qua SSE (Server-Sent Events).

**Headers**
| Key | Value |
|---|---|
| `Authorization` | `Bearer <token>` |
| `Accept` | `text/event-stream` |

**Response — `NotificationResponse`**
```json
{
  "id": "string",
  "content": "string",
  "readStatus": false,
  "type": "enum Type",
  "referenceId": "string"
}
```
### GET `/api/notifications`
Xem toàn bộ thông báo.

**Response — `List<NotificationResponse>`**
```json
[
  {
    "id": "string",
    "content": "string",
    "readStatus": false,
    "type": "enum Type",
    "referenceId": "string"
  }
]
```

## AvatarURL
### POST `/api/users/avatar`
Cập nhật ảnh đại diện của người dùng.

**Headers**
| Key | Value |
|---|---|
| `Authorization` | `Bearer <token>` |

**Body (form-data)**
| Key | Type | Value | Description |
|---|---|---|---|
| 'file' | 'File' | 'avt.png' | 'File ảnh avatar (.png, .jpg, . jpeg)' |


```json
{
    "code": 1000,
    "message": "Changing avatar is successful",
    "data": {
        "avatarURL": "/uploads/avatars/7567b0f9-7251-4e4e-be1b-6f0908b0f00b.png",
        "id": "7567b0f9-7251-4e4e-be1b-6f0908b0f00b",
        "name": "KietTruong",
        "username": "Kieta4"
    }
}
```
### DELETE `/api/users/avatar`

```json
{
    "code": 1000,
    "message": "Delete current avatar",
    "data": {
        "avatarURL": null,
        "id": "7567b0f9-7251-4e4e-be1b-6f0908b0f00b",
        "name": "KietTruong",
        "username": "Kieta4"
    }
}
```

## Type 

| Type | Ý nghĩa |
|------|---------|
| TASK_ASSIGNMENT (0) | Thông báo người nhận được phân công nhiệm vụ |
| DEADLINE (1) | Thông báo hết hạn (sắp hết hạn?) |
| COMMENT (2) | Thông báo khi có comment mới |
| ... | Có thể bổ sung thêm nếu người dùng được thêm xoá trong Space, Board |
---

## Role trong Space

| Role | Quyền |
|------|-------|
| OWNER (0) | Toàn quyền: xóa space, thay đổi role thành viên |
| ADMIN (1) | Xem được các Board riêng tư, thêm/xóa thành viên |
| MEMBER (2) | Xem được Space và các Board là thành viên |

---
## Error Codes

| Code | Enum | Message | HTTP |
|------|------|---------|------|
| **1001** | `INTERNAL_SERVER_ERROR` | Internal Server Error | 500 |
| **1002** | `VALIDATION_ERROR` | Validation Failed | 400 |
| **1003** | `UNAUTHENTICATED` | Unauthenticated | 401 |
| **1004** | `UNAUTHORIZED` | Unauthorized | 403 |
| **11xx** | *(User)* | | |
| **1100** | `USER_EXISTED` | User Existed | 400 |
| **1101** | `USER_NOT_FOUND` | User Not Found | 404 |
| **12xx** | *(Space)* | | |
| **1201** | `SPACE_NOT_FOUND` | Space Not Found | 404 |
| **1202** | `USER_EXISTED_IN_SPACE` | User Existed In The Space | 409 |
| **1203** | `USER_NOT_EXIST_IN_SPACE` | User Not Exist In The Space | 404 |
| **1204** | `OWNER_CANNOT_LEAVE_SPACE` | Owner Cannot Leave The Space | 403 |
| **13xx** | *(Board)* | | |
| **1301** | `BOARD_NOT_FOUND` | Board Not Found | 404 |
| **1302** | `USER_EXISTED_IN_BOARD` | User Existed In The Board | 409 |
| **1303** | `USER_NOT_EXIST_IN_BOARD` | User Not Exist In The Board | 404 |
| **1304** | `OWNER_CANNOT_LEAVE_BOARD` | Owner Cannot Leave The Board | 403 |
| **14xx** | *(Card)* | | |
| **1401** | `CARD_NOT_FOUND` | Card Not Found | 404 |
| **15xx** | *(Task)* | | |
| **1501** | `TASK_NOT_FOUND` | Task Not Found | 404 |
| **1503** | `USER_NOT_ASSIGNED_TO_TASK` | User Not Assigned To The Task | 404 |
| **16xx** | *(Comment)* | | |
| **1601** | `COMMENT_NOT_FOUND` | Comment Not Found | 404 |

