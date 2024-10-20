import axios from 'axios';
import config from '../config';

export const login = (usernameOrEmail, password) => {
  return axios.post(`${config.API_URL}/auth/login`, {
    usernameOrEmail,
    password
  });
};

export const register = (name, username, email, password) => {
  return axios.post(`${config.API_URL}/auth/register`, {
    name,
    username,
    email,
    password
  });
};
