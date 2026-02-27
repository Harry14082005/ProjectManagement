# Database Schema

Tài liệu mô tả cấu trúc cơ sở dữ liệu của hệ thống phân công công việc.

---

## Danh sách bảng

- [User](#user)
- [Space](#space)
- [SpaceUser](#spaceuser)
- [Board](#board)
- [BoardUser](#boarduser)
- [Card](#card)
- [Task](#task)
- [TaskAssignment](#taskassignment)
- [Comment](#comment)

---


## User

Lưu trữ thông tin người dùng trong hệ thống.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | String | Khóa chính |
| username | String | Tên đăng nhập, duy nhất |
| password | String | Mật khẩu (đã mã hóa) |
| name | String | Tên hiển thị |
| avatarURL | String | Đường dẫn ảnh đại diện |

---

## Space

Không gian làm việc, dùng để nhóm các Board liên quan lại với nhau.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | String | Khóa chính |
| name | String | Tên không gian làm việc |
| description | String | Mô tả |
| createdAt | Date | Thời điểm tạo |

---

## SpaceUser

Bảng trung gian thể hiện quan hệ nhiều-nhiều giữa User và Space, đồng thời gắn vai trò (Role) cho từng thành viên trong Space.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| userId | String | Khóa chính, khóa ngoại tham chiếu `User.id` |
| spaceId | String | Khóa chính, khóa ngoại tham chiếu `Space.id` |
| role | Enum |  vai trò của người dùng trong Space này `0: OWNER, 1: ADMIN, 2: MEMBER`|

---

## Board

Bảng (board) nằm trong một Space, dùng để tổ chức công việc theo từng dự án hoặc chủ đề.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | String | Khóa chính |
| spaceId | String | Khóa ngoại tham chiếu `Space.SpaceID` — Board thuộc Space nào |
| name | String | Tên board |
| description | String | Mô tả |
| isPrivate | Boolean | Board có ở chế độ riêng tư hay không |
| createdAt | Date | Thời điểm tạo |

---

## BoardUser

Bảng trung gian thể hiện quan hệ nhiều-nhiều giữa User và Board, xác định thành viên nào được tham gia board nào.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| boardId | String | Khóa chính, khóa ngoại tham chiếu `Board.id` |
| userId | String | Khóa chính, khóa ngoại tham chiếu `User.id` |
| isOwner | Boolean | Người dùng có phải là chủ sở hữu của board này không |

---

## Card

Cột (column) trong một Board, dùng để phân loại hoặc thể hiện trạng thái của các Task (ví dụ: To Do, In Progress, Done).

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | String | Khóa chính |
| boardId | String | Khóa ngoại tham chiếu `Board.id` — Card thuộc Board nào |
| name | String | Tên cột |
| createdAt | Date | Thời điểm tạo |

---

## Task

Đầu việc cụ thể nằm trong một Card.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | String | Khóa chính |
| cardId | String | Khóa ngoại tham chiếu `Card.id` — Task thuộc Card nào |
| name | String | Tên đầu việc |
| isCompleted | Boolean | Trạng thái hoàn thành |
| deadLine | Date | Thời hạn hoàn thành |
| createdAt | Date | Thời điểm tạo |

---

## TaskAssignment

Bảng trung gian thể hiện quan hệ nhiều-nhiều giữa User và Task, ghi nhận việc phân công ai phụ trách Task nào.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| userId | String | Khóa chính, khóa ngoại tham chiếu `User.id` |
| taskId | String | Khóa chính, khóa ngoại tham chiếu `Task.id` |

---

## Comment

Bình luận của người dùng trên một Task.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| id | String | Khóa chính |
| taskId | String | Khóa ngoại tham chiếu `Task.id` — Comment thuộc Task nào |
| userId | String | Khóa ngoại tham chiếu `User.id` — người viết bình luận |
| content | String | Nội dung bình luận |
| createdAt | Date | Thời điểm tạo |

---

## Tổng quan quan hệ

| Quan hệ | Mô tả |
|---------|-------|
| User — Space | Nhiều-nhiều qua bảng `SpaceUser`, kèm theo RoleID |
| Space — Board | Một Space có nhiều Board |
| Board — BoardUser | Nhiều-nhiều qua bảng `BoardUser` |
| Board — Card | Một Board có nhiều Card |
| Card — Task | Một Card có nhiều Task |
| Task — User | Nhiều-nhiều qua bảng `TaskAssignment` |
| Task — Comment | Một Task có nhiều Comment |
| User — Comment | Một User có thể viết nhiều Comment |
