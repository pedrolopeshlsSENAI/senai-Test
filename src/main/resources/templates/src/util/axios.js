import axios from 'axios'

axios.interceptors.request.use(
    (config) => {
      config.headers.Authorization = `Bearer ${ localStorage.token }`;
      config.headers._id =  localStorage.id
      return config;
    },
  
    (error) => {
      return Promise.reject(error);
    }
  );

  export default axios