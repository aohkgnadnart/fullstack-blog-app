// src/api/postApi.js
import axios from 'axios';
import config from '../config';

export const fetchPosts = (pageNo, pageSize, sortBy, sortDir) => {
  return axios.get(`${config.API_URL}/posts`, {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    }
  });
};

export const fetchPostsByTitle = (title, pageNo, pageSize, sortBy, sortDir) => {
  return axios.get(`${config.API_URL}/posts/title`, {
    params: {
      title,
      pageNo,
      pageSize,
      sortBy,
      sortDir
    }
  });
};

export const fetchPostsByTag = (tagId, pageNo, pageSize, sortBy, sortDir) => {
  return axios.get(`${config.API_URL}/posts/tags/${tagId}`, {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    }
  });
};

export const fetchPostsByUser = (userId, pageNo, pageSize, sortBy, sortDir) => {
  return axios.get(`${config.API_URL}/posts/users/${userId}`, {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    }
  });
};

export const createPost = (post) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`${config.API_URL}/posts`, post, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};

export const fetchPostById = (postId) => {
  return axios.get(`${config.API_URL}/posts/${postId}`);
};

export const fetchFollowingPosts = (pageNo, pageSize, sortBy, sortDir) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`${config.API_URL}/posts/following`, {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    },
    headers: {
      Authorization: `Bearer ${token}`,
    }
  });
};

export const trackPostViewers = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`${config.API_URL}/posts/${postId}/viewers`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const checkIfLiked = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`${config.API_URL}/posts/${postId}/is-like`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const checkIfDisliked = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`${config.API_URL}/posts/${postId}/is-dislike`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};


export const likePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`${config.API_URL}/posts/${postId}/like`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const dislikePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`${config.API_URL}/posts/${postId}/dislike`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const noLikePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`${config.API_URL}/posts/${postId}/no-like`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const noDislikePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`${config.API_URL}/posts/${postId}/no-dislike`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const deletePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`${config.API_URL}/posts/${postId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const updatePost = async (id, post) => {
  const token = localStorage.getItem('accessToken');
  return axios.put(`${config.API_URL}/posts/${id}`, post, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
