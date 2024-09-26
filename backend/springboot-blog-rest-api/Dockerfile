# Sử dụng image của OpenJDK để chạy Spring Boot
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR từ quá trình build vào container
COPY target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar /app/springboot-blog-rest-api.jar

# Expose cổng để truy cập vào Spring Boot app
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "springboot-blog-rest-api.jar"]
