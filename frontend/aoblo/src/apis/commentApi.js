// src/apis/commentApi.js
import axios from 'axios';

export const fetchCommentsByPostId = (postId) => {
  return axios.get(`http://localhost:8080/api/posts/${postId}/comments`);
};

export const createReply = (postId, parentCommentId, data) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/posts/${postId}/comments/${parentCommentId}`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const createComment = (postId, data) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/posts/${postId}/comments`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const deleteComment = (commentId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`http://localhost:8080/api/comments/${commentId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};