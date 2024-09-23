// Import các thư viện cần thiết
const express = require('express');
const http = require('http');
const { Server } = require('socket.io');

// Khởi tạo ứng dụng Express
const app = express();
const server = http.createServer(app);

// Khởi tạo server socket.io với http server
const io = new Server(server, {
  cors: {
    origin: "*", // Cho phép mọi domain kết nối đến server, bạn có thể tùy chỉnh lại cho phù hợp
    methods: ["GET", "POST"]
  }
});

// Xử lý kết nối từ client
io.on('connection', (socket) => {
  console.log('a user connected: ' + socket.id);

  // Nhận sự kiện từ client với tên 'message'
  socket.on("messageClient", (data) => {
    console.log('Received data from client:', data);

    // Phát tin nhắn đến tất cả các client, bao gồm cả client đã gửi tin nhắn
    io.emit("messageServer", { message: 'Hello from server!' });
  });

  // Xử lý khi client ngắt kết nối
  socket.on('disconnect', () => {
    console.log('user disconnected: ' + socket.id);
  });
});

// Cấu hình server lắng nghe tại cổng 9092
server.listen(9092, () => {
  console.log('Socket.IO server is running on port 9092');
});
