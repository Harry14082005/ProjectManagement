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
| UserID | INT | Khóa chính |
| Username | VARCHAR | Tên đăng nhập, duy nhất |
| Password | VARCHAR | Mật khẩu (đã mã hóa) |
| Name | VARCHAR | Tên hiển thị |
| AvatarURL | VARCHAR | Đường dẫn ảnh đại diện |

---

## Space

Không gian làm việc, dùng để nhóm các Board liên quan lại với nhau.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| SpaceID | INT | Khóa chính |
| SpaceName | VARCHAR | Tên không gian làm việc |
| SpaceDescription | TEXT | Mô tả |
| SpaceCreatedAt | DATETIME | Thời điểm tạo |

---

## SpaceUser

Bảng trung gian thể hiện quan hệ nhiều-nhiều giữa User và Space, đồng thời gắn vai trò (Role) cho từng thành viên trong Space.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| UserID | INT | Khóa chính, khóa ngoại tham chiếu `User.UserID` |
| SpaceID | INT | Khóa chính, khóa ngoại tham chiếu `Space.SpaceID` |
| Role | Enum |  vai trò của người dùng trong Space này `0: OWNER, 1: ADMIN, 2: MEMBER|

---

## Board

Bảng (board) nằm trong một Space, dùng để tổ chức công việc theo từng dự án hoặc chủ đề.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| BoardID | INT | Khóa chính |
| SpaceID | INT | Khóa ngoại tham chiếu `Space.SpaceID` — Board thuộc Space nào |
| BoardName | VARCHAR | Tên board |
| BoardDescription | TEXT | Mô tả |
| IsPrivate | BOOLEAN | Board có ở chế độ riêng tư hay không |
| BoardCreatedAt | DATETIME | Thời điểm tạo |

---

## BoardUser

Bảng trung gian thể hiện quan hệ nhiều-nhiều giữa User và Board, xác định thành viên nào được tham gia board nào.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| BoardID | INT | Khóa chính, khóa ngoại tham chiếu `Board.BoardID` |
| UserID | INT | Khóa chính, khóa ngoại tham chiếu `User.UserID` |
| IsOwner | BOOLEAN | Người dùng có phải là chủ sở hữu của board này không |

---

## Card

Cột (column) trong một Board, dùng để phân loại hoặc thể hiện trạng thái của các Task (ví dụ: To Do, In Progress, Done).

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| CardID | INT | Khóa chính |
| BoardID | INT | Khóa ngoại tham chiếu `Board.BoardID` — Card thuộc Board nào |
| CardName | VARCHAR | Tên cột |
| CardCreatedAt | DATETIME | Thời điểm tạo |

---

## Task

Đầu việc cụ thể nằm trong một Card.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| TaskID | INT | Khóa chính |
| CardID | INT | Khóa ngoại tham chiếu `Card.CardID` — Task thuộc Card nào |
| TaskName | VARCHAR | Tên đầu việc |
| IsCompleted | BOOLEAN | Trạng thái hoàn thành |
| TaskDeadLine | DATETIME | Thời hạn hoàn thành |
| TaskCreatedAt | DATETIME | Thời điểm tạo |

---

## TaskAssignment

Bảng trung gian thể hiện quan hệ nhiều-nhiều giữa User và Task, ghi nhận việc phân công ai phụ trách Task nào.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| UserID | INT | Khóa chính, khóa ngoại tham chiếu `User.UserID` |
| TaskID | INT | Khóa chính, khóa ngoại tham chiếu `Task.TaskID` |

---

## Comment

Bình luận của người dùng trên một Task.

| Cột | Kiểu | Mô tả |
|-----|------|-------|
| CommentID | INT | Khóa chính |
| TaskID | INT | Khóa ngoại tham chiếu `Task.TaskID` — Comment thuộc Task nào |
| UserID | INT | Khóa ngoại tham chiếu `User.UserID` — người viết bình luận |
| Content | TEXT | Nội dung bình luận |
| CreatedAt | DATETIME | Thời điểm tạo |

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
