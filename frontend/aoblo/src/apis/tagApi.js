import axios from 'axios';
import config from '../config';

export const fetchTags = (name) => {
  return axios.get(`${config.API_URL}/tags/top5`, {
    params: {
      name
    }
  });
};
