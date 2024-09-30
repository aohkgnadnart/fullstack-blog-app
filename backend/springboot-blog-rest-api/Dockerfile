# Sử dụng image Maven để build Spring Boot ứng dụng
FROM maven:3.9.9-eclipse-temurin-17 as builder

# Đặt thư mục làm việc trong container cho quá trình build
WORKDIR /app

# Sao chép file pom.xml và tải các dependency (bước này được thực hiện trước để cache tốt hơn)
COPY pom.xml .

# Tải xuống các dependency cần thiết
RUN mvn dependency:go-offline

# Sao chép toàn bộ mã nguồn vào container
COPY src ./src

# Build dự án sử dụng Maven và bỏ qua các bài kiểm tra
RUN mvn clean install -DskipTests

# Sử dụng image của OpenJDK để chạy Spring Boot
FROM eclipse-temurin:17-jdk

# Đặt thư mục làm việc trong container cho ứng dụng chạy
WORKDIR /app

# Sao chép file JAR từ quá trình build vào container
COPY --from=builder /app/target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar /app/springboot-blog-rest-api.jar

# Expose cổng để truy cập vào Spring Boot app
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "springboot-blog-rest-api.jar"]
