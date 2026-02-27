# CT240 Backend API Documentation

API backend cho hệ thống phân công công việc. Base URL: `http://localhost:8080`

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
- [Template Request & Response](#template)

---

## Auth

### POST `/auth/register`

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

### POST `/auth/login`

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

### GET `/api/user/profile`

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

### PUT `/api/user/update`

Cập nhật thông tin người dùng.

**Request body — `UserUpdateRequest`**
```json
{
  "name": "string (optional)",
  "avatarURL": "string (optional)",
  "password": "string (optional)"
}
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

**Response — `List<SpaceUserResponse>`**
```json
[
  {
    "userId": "string",
    "spaceId": "string",
    "role": "OWNER | ADMIN | MEMBER",
    "user": {
      "id": "string",
      "username": "string",
      "name": "string",
      "avatarURL": "string"
    }
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

Xóa thành viên khỏi Space. Yêu cầu quyền OWNER.

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
  "deadLine": "2024-12-31T00:00:00Z (optional)"
}
```

**Response — `TaskResponse`**
```json
{
  "id": "string (UUID)",
  "cardId": "string",
  "name": "string",
  "isCompleted": false,
  "deadLine": "2024-12-31T00:00:00Z",
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
  "deadLine": "date (optional)"
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

## Role trong Space

| Role | Quyền |
|------|-------|
| OWNER (0) | Toàn quyền: xóa space, thay đổi role thành viên |
| ADMIN (1) | Quản lý board, thêm/xóa thành viên |
| MEMBER (2) | Xem và tham gia công việc |

---

## Template

### Request

```java
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class XxxRequest {
    String field1;
    String field2;
}
```

### Response

```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class XxxResponse {
    String id;
    String field1;
    String field2;
}
```
