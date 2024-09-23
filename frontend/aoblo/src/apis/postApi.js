// src/api/postApi.js
import axios from 'axios';

export const fetchPosts = (pageNo, pageSize, sortBy, sortDir) => {
  return axios.get(`http://localhost:8080/api/posts`, {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    }
  });
};

export const fetchPostsByTitle = (title, pageNo, pageSize, sortBy, sortDir) => {
  return axios.get(`http://localhost:8080/api/posts/title`, {
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
  return axios.get(`http://localhost:8080/api/posts/tags/${tagId}`, {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    }
  });
};

export const fetchPostsByUser = (userId, pageNo, pageSize, sortBy, sortDir) => {
  return axios.get(`http://localhost:8080/api/posts/users/${userId}`, {
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
  return axios.post('http://localhost:8080/api/posts', post, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};

export const fetchPostById = (postId) => {
  return axios.get(`http://localhost:8080/api/posts/${postId}`);
};

export const fetchFollowingPosts = (pageNo, pageSize, sortBy, sortDir) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`http://localhost:8080/api/posts/following`, {
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
  return axios.post(`http://localhost:8080/api/posts/${postId}/viewers`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const checkIfLiked = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`http://localhost:8080/api/posts/${postId}/is-like`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const checkIfDisliked = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`http://localhost:8080/api/posts/${postId}/is-dislike`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};


export const likePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/posts/${postId}/like`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const dislikePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/posts/${postId}/dislike`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const noLikePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`http://localhost:8080/api/posts/${postId}/no-like`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const noDislikePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`http://localhost:8080/api/posts/${postId}/no-dislike`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const deletePost = (postId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`http://localhost:8080/api/posts/${postId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const updatePost = async (id, post) => {
  const token = localStorage.getItem('accessToken');
  return axios.put(`http://localhost:8080/api/posts/${id}`, post, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
