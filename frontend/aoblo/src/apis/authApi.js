import axios from 'axios';

export const login = (usernameOrEmail, password) => {
  return axios.post('http://localhost:8080/api/auth/login', {
    usernameOrEmail,
    password
  });
};

export const register = (name, username, email, password) => {
  return axios.post('http://localhost:8080/api/auth/register', {
    name,
    username,
    email,
    password
  });
};
