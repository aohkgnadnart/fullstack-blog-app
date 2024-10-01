const config = {
    API_URL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
    SOCKETIO: process.env.REACT_APP_SOCKETIO || 'http://localhost:9092',
  };
  
  export default config;