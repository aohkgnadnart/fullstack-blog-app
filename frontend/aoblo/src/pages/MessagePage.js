import React, { useState, useEffect, useContext, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { UserContext } from '../App';
import { io } from "socket.io-client";
import { fetchMessageHistory, sendMessage } from '../apis/messageApi';
import './MessagePage.css'; 

const MessagePage = () => {
  const { userId } = useParams();
  const { user } = useContext(UserContext);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [pageNo, setPageNo] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const chatHistoryRef = useRef(null);
  const socketRef = useRef(null);

  useEffect(() => {
    if (!user) {
      toast.warning('Bạn cần đăng nhập để sử dụng tính năng nhắn tin');
      return;
    }

    // Chỉ tạo socket một lần khi component mount
    if (!socketRef.current) {
      socketRef.current = io("http://localhost:9092", {
        transports: ["websocket", "polling"]
      });

      socketRef.current.on("connect", () => {
        console.log("Connected to socket.io server" + socketRef.current.id);
      });

      socketRef.current.on("connect_error", (error) => {
        console.error("Connection error:", error);
      });

      socketRef.current.on("disconnect", () => {
        console.log("Disconnected from socket.io server");
      });

      // Lắng nghe sự kiện message từ server và cập nhật lại lịch sử tin nhắn
      socketRef.current.on("messageServer", (data) => {
        console.log("Received socket data:", data);
        fetchMessages(0, false); // Fetch lại từ trang đầu tiên để đảm bảo tin nhắn mới nhất được hiển thị
      });
    }

    fetchMessages(pageNo, false);

    // Ngắt kết nối khi component unmount
    return () => {
      if (socketRef.current) {
        socketRef.current.disconnect();
        socketRef.current = null;
      }
    };
  }, [user, userId]);

  const fetchMessages = async (page, append) => {
    try {
      const response = await fetchMessageHistory(userId, page);
      console.log("Fetched messages:", response.data.content);

      setMessages(prevMessages => 
        append ? [...prevMessages, ...response.data.content] : response.data.content
      );
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching messages', error);
    }
  };

  const handleSendMessage = async () => {
    try {
      await sendMessage(userId, newMessage);
      setNewMessage('');
      socketRef.current.emit("messageClient", { userId: userId });
      fetchMessages(0, false);
      toast.success('Tin nhắn đã được gửi');
    } catch (error) {
      console.error('Error sending message', error);
      toast.error('Lỗi khi gửi tin nhắn');
    }
  };

  const handleScroll = () => {
    const { scrollTop, scrollHeight, clientHeight } = chatHistoryRef.current;
    if (scrollTop + clientHeight >= scrollHeight && pageNo + 1 < totalPages) {
      setPageNo(prevPageNo => prevPageNo + 1);
      fetchMessages(pageNo + 1, true);
    }
  };

  return (
    <div className="message-page">
      <h1 className="message-page-title">Chat with User {userId}</h1>
      <div className="chat-input-container">
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Nhập tin nhắn..."
          className="chat-input"
        />
        <button onClick={handleSendMessage} className="chat-send-button">Send</button>
      </div>
      <div className="chat-history" ref={chatHistoryRef} onScroll={handleScroll}>
        {messages.map((message, index) => (
          <div
            key={`${message.id}-${index}`}
            className={`chat-message ${message.sender.id === user.id ? 'sent' : 'received'}`}
          >
            <div className="message-content">
              <b>{message.sender.username}</b>: {message.content}
            </div>
            <div className="message-time">
              {new Date(message.createAt).toLocaleString()}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MessagePage;
