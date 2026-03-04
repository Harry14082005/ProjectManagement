# 📋 Phase 1 — Khởi tạo & Thiết kế hệ thống `v0.1.0`

> **Dự án:** TaskFlow — Hệ Thống Quản Lý Công Việc (Trello Clone)  
> **Công nghệ:** Java Spring Boot · Vue.js 3  
> **Thời gian:** 2026-03-09 → 2026-03-22  
> **Trạng thái:** 🟡 Đang thực hiện  

---

## 🎯 Mục Tiêu Giai Đoạn

Thiết lập nền tảng kỹ thuật và thiết kế toàn bộ kiến trúc hệ thống trước khi bắt đầu code. Bao gồm: ERD, thiết kế API contract, wireframe UI và cấu hình môi trường dev.

## 🛠️ Công Nghệ Sử Dụng

- `PostgreSQL`
- `Draw.io`
- `Figma`
- `Docker`
- `Git`
- `Java 17`
- `Vue.js 3`

## 👥 Phân Công Công Việc

| MSSV | Thành viên | Vai trò | Công việc | Trạng thái |
|------|-----------|---------|-----------|------------|
| B2303812 | Bùi Đông Hiển | Team Lead / Architect | Thiết kế ERD đầy đủ (User, Space, Board, Card, Task, Comment, Attachment) | 🔄 In Progress |
| | | | Thiết lập Git repo, quy ước branch & commit convention | 📋 Todo |
| | | | Viết tài liệu API Contract (RESTful endpoints, request/response schema) | 📋 Todo |
| B2303824 | Trương Tuấn Kiệt | Backend Dev | Cài đặt Spring Boot 3.x, cấu hình Maven, project structure | 📋 Todo |
| | | | Thiết lập Docker Compose: PostgreSQL + Spring Boot + Redis | 📋 Todo |
| | | | Cấu hình Spring Security skeleton + JWT filter chain | 📋 Todo |
| B2303849 | Châu Ngọc Thịnh | Backend Dev | Tạo các Entity JPA theo ERD (User, Space, Board, Card, Task) | 📋 Todo |
| | | | Cấu hình Flyway migration scripts cho schema ban đầu | 📋 Todo |
| | | | Thiết lập cấu trúc Repository & Service layer skeleton | 📋 Todo |
| B2303850 | Trần Phú Thịnh | Frontend Dev | Khởi tạo dự án Vue.js 3 + Vite + Pinia + Vue Router | 📋 Todo |
| | | | Thiết kế wireframe các màn hình chính (Login, Dashboard, Board, Card detail) | 📋 Todo |
| | | | Cài đặt Axios, cấu hình interceptor cho JWT token | 📋 Todo |
| B2303853 | Nguyễn Thái Toàn | QA / DevOps | Viết test plan tổng thể cho toàn dự án (các module cần test) | 📋 Todo |
| | | | Cấu hình CI pipeline (GitHub Actions): build + lint check | 📋 Todo |
| | | | Xác nhận môi trường Docker chạy ổn định trên máy tất cả thành viên | 📋 Todo |

## 📝 Ghi Chú Cá Nhân

### Bùi Đông Hiển (B2303812)
Phụ trách chung — review toàn bộ output của giai đoạn 1

### Trương Tuấn Kiệt (B2303824)
Tham chiếu tài liệu thiết kế phần mềm mục Kiến trúc 3-Tier

### Châu Ngọc Thịnh (B2303849)
Tham chiếu ERD từ Hiển để tạo Entity đúng quan hệ

### Trần Phú Thịnh (B2303850)
Wireframe dùng Figma, export share link để team review

### Nguyễn Thái Toàn (B2303853)
Kiểm tra Docker chạy được trên Windows, macOS, Linux

## ⚠️ Ghi Chú & Rủi Ro

Giai đoạn này tập trung 100% vào thiết kế — KHÔNG viết code nghiệp vụ. Cần thống nhất API contract trước tuần 3 để Frontend và Backend làm song song.

---

*Cập nhật lần cuối: 04/03/2026*
