// src/apis/commentApi.js
import axios from 'axios';
import config from '../config';

export const fetchCommentsByPostId = (postId) => {
  return axios.get(`${config.API_URL}/posts/${postId}/comments`);
};

export const createReply = (postId, parentCommentId, data) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`${config.API_URL}/posts/${postId}/comments/${parentCommentId}`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const createComment = (postId, data) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`${config.API_URL}/posts/${postId}/comments`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const deleteComment = (commentId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`${config.API_URL}/comments/${commentId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};