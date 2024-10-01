// src/apis/messageApi.js
import axios from 'axios';
import config from '../config';

export const fetchMessageHistory = (userId, pageNo = 0, pageSize = 10) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`${config.API_URL}/messages/history/${userId}?pageNo=${pageNo}&pageSize=${pageSize}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const sendMessage = (userId, content) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`${config.API_URL}/messages/send/${userId}`, { content }, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
