// src/apis/messageApi.js
import axios from 'axios';

export const fetchMessageHistory = (userId, pageNo = 0, pageSize = 10) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`http://localhost:8080/api/messages/history/${userId}?pageNo=${pageNo}&pageSize=${pageSize}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const sendMessage = (userId, content) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/messages/send/${userId}`, { content }, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
