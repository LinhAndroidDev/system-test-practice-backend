# First Spring Boot App

Ứng dụng Spring Boot đầu tiên - Hệ thống quản lý câu hỏi thi cử với React Frontend

## 📋 Tổng quan

Đây là một ứng dụng web full-stack được xây dựng với Spring Boot (backend) và React (frontend). Ứng dụng cung cấp hệ thống quản lý người dùng, môn học và câu hỏi thi cử với khả năng tạo và thực hiện bài thi.

## 🚀 Tính năng chính

- **Quản lý người dùng**: Đăng ký và đăng nhập
- **Quản lý môn học**: Tạo và quản lý các môn học
- **Quản lý câu hỏi**: Tạo câu hỏi trắc nghiệm với 4 đáp án
- **Hệ thống thi**: Tạo bài thi và theo dõi kết quả
- **API RESTful**: Cung cấp API cho frontend React

## 🛠️ Công nghệ sử dụng

### Backend
- **Spring Boot 3.5.5** - Framework chính
- **Java 17** - Ngôn ngữ lập trình
- **Spring Data JPA** - ORM và quản lý database
- **MySQL** - Cơ sở dữ liệu
- **Lombok** - Giảm boilerplate code
- **Thymeleaf** - Template engine

### Frontend
- **React** - Framework JavaScript (build sẵn)
- **Poppins Font** - Typography

### Build Tool
- **Gradle** - Build automation

## 📁 Cấu trúc dự án

```
first-spring-app/
├── src/
│   ├── main/
│   │   ├── java/net/javaguides/springboot/
│   │   │   ├── FirstSpringbootAppApplication.java    # Main application class
│   │   │   ├── WelcomeController.java                # Welcome controller
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java                    # CORS configuration
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java               # Authentication APIs
│   │   │   │   ├── QuestionController.java           # Question management APIs
│   │   │   │   └── SubjectController.java            # Subject management APIs
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java                 # Login DTO
│   │   │   │   ├── RegisterRequest.java              # Register DTO
│   │   │   │   ├── QuestionRequest.java              # Question DTO
│   │   │   │   └── SubjectRequest.java               # Subject DTO
│   │   │   ├── entity/
│   │   │   │   ├── User.java                         # User entity
│   │   │   │   ├── Subject.java                      # Subject entity
│   │   │   │   ├── Question.java                     # Question entity
│   │   │   │   ├── Exam.java                         # Exam entity
│   │   │   │   ├── ExamResult.java                   # Exam result entity
│   │   │   │   └── ExamAnswer.java                   # Exam answer entity
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java               # User data access
│   │   │   │   ├── SubjectRepository.java            # Subject data access
│   │   │   │   └── QuestionRepository.java           # Question data access
│   │   │   ├── service/
│   │   │   │   ├── UserService.java                  # User business logic
│   │   │   │   └── ExamService.java                  # Exam business logic
│   │   │   ├── response/
│   │   │   │   ├── BaseResponse.java                 # Base response wrapper
│   │   │   │   ├── QuestionResponse.java             # Question response DTO
│   │   │   │   └── SubjectResponse.java              # Subject response DTO
│   │   │   ├── mapper/
│   │   │   │   └── QuestionMapper.java               # Entity-DTO mapper
│   │   │   └── utils/
│   │   │       └── Constants.java                    # Application constants
│   │   └── resources/
│   │       ├── application.properties                # Application configuration
│   │       ├── templates/
│   │       │   └── index.html                        # Welcome page template
│   │       └── static/                               # Static resources (React build)
│   └── test/
│       └── java/net/javaguides/springboot/
│           └── FirstSpringbootAppApplicationTests.java
├── build.gradle                                      # Build configuration
├── settings.gradle                                   # Project settings
└── README.md                                         # Project documentation
```

## 🗄️ Cơ sở dữ liệu

Ứng dụng sử dụng MySQL với các bảng chính:

- **users**: Thông tin người dùng (id, name, email, password)
- **subject**: Danh sách môn học (id, name_subject)
- **questions**: Câu hỏi trắc nghiệm (id, subject_id, content, option_a, option_b, option_c, option_d, correct_answer)
- **exams**: Bài thi (id, subject_id, title, duration_seconds)
- **exam_results**: Kết quả thi
- **exam_answers**: Đáp án của người dùng

## ⚙️ Cài đặt và chạy ứng dụng

### Yêu cầu hệ thống
- Java 17 hoặc cao hơn
- MySQL 8.0 hoặc cao hơn
- Gradle (được include trong project)

### Các bước cài đặt

1. **Clone repository**
   ```bash
   git clone <repository-url>
   cd first-spring-app
   ```

2. **Cấu hình database**
   - Tạo database MySQL tên `manage_user`
   - Cập nhật thông tin kết nối trong `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/manage_user?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
     spring.datasource.username=root
     spring.datasource.password=your_password
     ```

3. **Build và chạy ứng dụng**
   ```bash
   # Sử dụng Gradle Wrapper
   ./gradlew bootRun
   
   # Hoặc build JAR và chạy
   ./gradlew build
   java -jar build/libs/first-spring-app-0.0.1-SNAPSHOT.jar
   ```

4. **Truy cập ứng dụng**
   - Backend API: `http://localhost:8080`
   - Welcome page: `http://localhost:8080/`
   - API endpoints: `http://localhost:8080/api/`

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - Đăng ký người dùng mới
- `POST /api/auth/login` - Đăng nhập

### Subjects
- `GET /api/subjects` - Lấy danh sách môn học
- `POST /api/subjects` - Tạo môn học mới

### Questions
- `GET /api/questions` - Lấy danh sách câu hỏi
- `POST /api/questions` - Tạo câu hỏi mới

## 🌐 CORS Configuration

Ứng dụng được cấu hình để cho phép CORS từ React frontend chạy trên `http://localhost:3000`.

## 🧪 Testing

Chạy test cases:
```bash
./gradlew test
```

## 📝 Cấu hình

### Application Properties
- Database connection settings
- JPA/Hibernate configuration
- SQL logging enabled
- Auto-update schema mode

### Build Configuration
- Spring Boot 3.5.5
- Java 17 toolchain
- Dependencies: Web, JPA, MySQL, Thymeleaf, Lombok

## 🤝 Đóng góp

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Mở Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 👥 Tác giả

- **Java Guides** - *Initial work* - [JavaGuides](https://github.com/RameshMF)

## 📞 Liên hệ

Nếu có câu hỏi hoặc cần hỗ trợ, vui lòng tạo issue trong repository.

---

**Lưu ý**: Đây là ứng dụng demo Spring Boot đầu tiên, phù hợp cho việc học tập và phát triển thêm các tính năng mới.
