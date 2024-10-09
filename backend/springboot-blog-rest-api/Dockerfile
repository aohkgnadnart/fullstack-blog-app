# Sử dụng image của OpenJDK để chạy Spring Boot
FROM eclipse-temurin:17-jdk

# Đặt thư mục làm việc trong container cho ứng dụng chạy
WORKDIR /app

# Sao chép file và folder cần để tạo file jar vào container
COPY mvnw .
COPY .mvn/ .mvn/
COPY src/ src/
COPY pom.xml .

# Xóa thư mục target, biên dịch và đóng gói dự án thành file jar, bỏ qua các bài kiểm tra
RUN ./mvnw clean package -DskipTests

# Expose cổng để truy cập vào Spring Boot app
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar"]
