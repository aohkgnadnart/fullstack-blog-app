import axios from 'axios';

export const fetchTags = (name) => {
  return axios.get(`http://localhost:8080/api/tags/top5`, {
    params: {
      name
    }
  });
};
