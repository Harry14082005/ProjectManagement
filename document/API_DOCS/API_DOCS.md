# TaskFlow API Docs

**Base URL:** `http://localhost:8080/api`  
**Auth:** Các endpoint có 🔒 yêu cầu header `Authorization: Bearer <token>`

---

## Authentication

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| POST | `/auth/register` | Đăng ký tài khoản |
| POST | `/auth/login` | Đăng nhập → trả về JWT token |
| POST | `/auth/logout` | Đăng xuất |

---

## Space 🔒

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/spaces` | Danh sách space của tôi |
| POST | `/spaces` | Tạo space mới |
| GET | `/spaces/{id}` | Chi tiết space |
| PUT | `/spaces/{id}` | Cập nhật space |
| DELETE | `/spaces/{id}` | Xoá space |
| GET | `/spaces/{id}/members` | Danh sách member |
| POST | `/spaces/{id}/members` | Thêm member |
| PUT | `/spaces/{id}/members/{userId}/role` | Đổi role member |
| DELETE | `/spaces/{id}/members/{userId}` | Kick member |

---

## Board 🔒

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/spaces/{id}/boards` | Danh sách board |
| POST | `/spaces/{id}/boards` | Tạo board |
| PUT | `/spaces/{id}/boards/{boardId}` | Cập nhật board |
| DELETE | `/spaces/{id}/boards/{boardId}` | Xoá board |
| GET | `/boards/{id}/members` | Danh sách board member |
| POST | `/boards/{id}/members` | Thêm member vào board |
| DELETE | `/boards/{id}/members/{userId}` | Xoá member khỏi board |

---

## Card 🔒

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/boards/{id}/cards` | Danh sách card |
| POST | `/boards/{id}/cards` | Tạo card |
| PUT | `/boards/{id}/cards/{cardId}` | Đổi tên card |
| DELETE | `/boards/{id}/cards/{cardId}` | Xoá card |

---

## Task 🔒

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/cards/{id}/tasks` | Danh sách task |
| POST | `/cards/{id}/tasks` | Tạo task |
| PUT | `/tasks/{id}` | Cập nhật task |
| DELETE | `/tasks/{id}` | Xoá task |
| POST | `/tasks/{id}/assign` | Assign task cho member |
| DELETE | `/tasks/{id}/assign/{userId}` | Unassign task |

---

## Error Codes

> Quy ước: code chia theo nhóm, tên enum theo pattern `ENTITY_VẤNĐỀ`

| Code | Enum | Message | HTTP |
|------|------|---------|------|
| **1000** | `SUCCESS` | Thành công | 200 |
| **1001** | `UNCATEGORIZED_ERROR` | Lỗi không xác định | 500 |
| **1002** | `INVALID_REQUEST` | Request không hợp lệ | 400 |
| **1003** | `UNAUTHENTICATED` | Chưa đăng nhập | 401 |
| **1004** | `UNAUTHORIZED` | Không có quyền thực hiện | 403 |
| **11xx** | *(User)* | | |
| **1100** | `USER_EXISTED` | Username đã tồn tại | 400 |
| **1101** | `USER_NOT_FOUND` | Không tìm thấy user | 404 |
| **12xx** | *(Space)* | | |
| **1200** | `SPACE_NOT_FOUND` | Không tìm thấy space | 404 |
| **13xx** | *(Board)* | | |
| **1300** | `BOARD_NOT_FOUND` | Không tìm thấy board | 404 |
| **14xx** | *(Card)* | | |
| **1400** | `CARD_NOT_FOUND` | Không tìm thấy card | 404 |
| **15xx** | *(Task)* | | |
| **1500** | `TASK_NOT_FOUND` | Không tìm thấy task | 404 |
