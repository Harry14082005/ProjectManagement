# Ứng Dụng Quản Lý Công Việc Nhóm

> Ứng dụng quản lý công việc nhóm quy mô vừa, hỗ trợ các nhóm làm việc cộng tác hiệu quả trong việc phân công, theo dõi và hoàn thành công việc.

---

## Mục Lục

- [Giới thiệu](#-giới-thiệu)
- [Chức năng chính](#-chức-năng-chính)
- [Kiến trúc hệ thống](#-kiến-trúc-hệ-thống)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Design Patterns](#-design-patterns)
- [Cài đặt & Chạy dự án](#-cài-đặt--chạy-dự-án)
- [Kiểm thử](#-kiểm-thử)
- [Tài liệu](#-tài-liệu)
- [Contributors](#-contributors)

---

## Giới Thiệu

**TeamTask** là ứng dụng quản lý công việc nhóm được xây dựng nhằm giúp các nhóm làm việc tổ chức, phân công và theo dõi tiến độ công việc một cách trực quan và hiệu quả. Hệ thống hỗ trợ phân quyền người dùng, thông báo thời gian thực và thống kê tiến độ dự án.

---

## Chức Năng Chính

### Quản lý Dự án
- Tạo, chỉnh sửa, xóa dự án
- Phân quyền quản lý dự án cho từng thành viên

### Quản lý Công việc
- Tạo công việc, gán người thực hiện, đặt deadline
- Quản lý trạng thái: `To Do` → `In Progress` → `Done` / `Cancel`

### Quản lý Người dùng
- Đăng ký, đăng nhập tài khoản
- Phân vai trò: **Admin** | **Manager** | **Member**

### Thông báo
- Thông báo khi trạng thái công việc thay đổi
- Cảnh báo khi deadline sắp đến

### Bình luận & Trao đổi
- Thảo luận trực tiếp trong từng công việc

### Thống kê & Báo cáo
- Thống kê số lượng công việc theo trạng thái
- Theo dõi tiến độ tổng thể của dự án

---

Nguyên tắc thiết kế:
- **Low Coupling – High Cohesion**: Các tầng độc lập, dễ bảo trì và mở rộng
- Tách biệt rõ ràng giữa giao diện, xử lý nghiệp vụ và dữ liệu

---

## Công Nghệ Sử Dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ | Java |
| IDE | IntelliJ IDEA |
| Build Tool | Maven |
| Framework | Spring Boot |
| Database | MySQL |
| Frontend | Vue.js |
| UML | draw.io / StarUML |

---

## Design Patterns

| Pattern | Mục đích áp dụng |
|---|---|
| **Observer** | Theo dõi thay đổi trạng thái công việc, gửi thông báo tự động |
| **Strategy** | Chiến lược cập nhật trạng thái công việc linh hoạt |
| **Factory** | Tạo đối tượng Task, User, Notification một cách thống nhất |
| **Template Method** | Khung sườn xử lý báo cáo và thống kê tiến độ |

---

## Cài Đặt & Chạy Dự Án

### Yêu cầu
- Java 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+


> Frontend chạy mặc định tại `http://localhost:5173`, kết nối backend tại `http://localhost:8080/api`

### Cấu hình Database

Tạo database và cập nhật thông tin kết nối trong `src/main/resources/application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ct240_db
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  signerKey: "your_secret_key_here"
```

> **Lưu ý bảo mật**: Không commit `signerKey` thật lên repository. Sử dụng biến môi trường hoặc file `.env` riêng.

---

## Tài Liệu

Tài liệu thiết kế UML (Class Diagram, Sequence Diagram, Use-case Diagram) và các tài liệu liên quan:

 [Google Drive – Tài liệu dự án](https://drive.google.com/drive/u/3/folders/14JiELfOp1bUiUuP1LGtxoaCE3eD2ocD_)

---

## Contributors

| MSSV | Họ và Tên |
|---|---|
| B2303812 | Bùi Đông Hiển |
| B2303824 | Trương Tuấn Kiệt |
| B2303849 | Châu Ngọc Thịnh |
| B2303850 | Trần Phú Thịnh |
| B2303853 | Nguyễn Thái Toàn |

---
