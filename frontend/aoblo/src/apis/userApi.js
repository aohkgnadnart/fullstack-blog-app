// src/apis/userApi.js
import axios from 'axios';

export const fetchUserByUsernameOrEmail = (usernameOrEmail, token) => {
  return axios.get(`http://localhost:8080/api/users`, {
    params: { usernameOrEmail },
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};

export const fetchTop5UsersByUsername = (username) => {
  return axios.get(`http://localhost:8080/api/users/top5`, {
    params: {
      username
    }
  });
};

export const fetchUserById = (id) => {
  return axios.get(`http://localhost:8080/api/users/${id}`);
};

export const fetchMe = (token) => {
  return axios.get('http://localhost:8080/api/users/me', {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};

export const changePassword = (data) => {
  const token = localStorage.getItem('accessToken');
  return axios.put('http://localhost:8080/api/users/change-password', data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const checkIfFollowing = (userId) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`http://localhost:8080/api/users/is-following/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const followUser = (userId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/users/follow/${userId}`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const unfollowUser = (userId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`http://localhost:8080/api/users/unfollow/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const checkIfFriend = (userId) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`http://localhost:8080/api/users/is-friend/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const sendFriendRequest = (userId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/users/send-friend-request/${userId}`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const unfriendUser = (userId) => {
  const token = localStorage.getItem('accessToken');
  return axios.delete(`http://localhost:8080/api/users/unfriend/${userId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const fetchAllUsers = (pageNo, pageSize, sortBy = 'username', sortDir = 'asc') => {
  const token = localStorage.getItem('accessToken');
  return axios.get('http://localhost:8080/api/users', {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const fetchFollowers = (pageNo, pageSize, sortBy = 'username', sortDir = 'asc') => {
  const token = localStorage.getItem('accessToken');
  return axios.get('http://localhost:8080/api/users/followers', {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const fetchFollowing = (pageNo, pageSize, sortBy = 'username', sortDir = 'asc') => {
  const token = localStorage.getItem('accessToken');
  return axios.get('http://localhost:8080/api/users/following', {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const fetchFriends = (pageNo, pageSize, sortBy = 'username', sortDir = 'asc') => {
  const token = localStorage.getItem('accessToken');
  return axios.get('http://localhost:8080/api/users/friends', {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const fetchReceivedFriendRequests = (pageNo, pageSize, sortBy = 'username', sortDir = 'asc') => {
  const token = localStorage.getItem('accessToken');
  return axios.get('http://localhost:8080/api/users/received-friend-requests', {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const fetchSentFriendRequests = (pageNo, pageSize, sortBy = 'username', sortDir = 'asc') => {
  const token = localStorage.getItem('accessToken');
  return axios.get('http://localhost:8080/api/users/sent-friend-requests', {
    params: {
      pageNo,
      pageSize,
      sortBy,
      sortDir
    },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const downgradeFromAdmin = () => {
  const token = localStorage.getItem('accessToken');
  return axios.delete('http://localhost:8080/api/users/downgrade-from-admin', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

export const upgradeToAdmin = (userId) => {
  const token = localStorage.getItem('accessToken');
  return axios.post(`http://localhost:8080/api/users/${userId}/upgrade-to-admin`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};


export const fetchUsersLikedPost = (postId, pageNo, pageSize) => {
  const token = localStorage.getItem('accessToken');
  return axios.get(`http://localhost:8080/api/users/like-post/${postId}`, {
    params: { pageNo, pageSize },
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};