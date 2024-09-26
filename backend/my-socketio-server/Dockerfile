# Sử dụng Node.js phiên bản 14 làm base image
FROM node:14-alpine

# Thiết lập thư mục làm việc trong container
WORKDIR /usr/src/app

# Sao chép file package.json và package-lock.json vào container
COPY package*.json ./

# Cài đặt các dependencies
RUN npm install

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Mở cổng mà ứng dụng sẽ chạy
EXPOSE 9092

# Lệnh để chạy ứng dụng khi container khởi động
CMD ["node", "server.js"]
