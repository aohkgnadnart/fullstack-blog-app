import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { UserContext } from '../App';
import { login } from '../apis/authApi';
import { fetchMe } from '../apis/userApi'; // Import hàm fetchMe
import './Login.css';

const Login = () => {
  const [usernameOrEmail, setUsernameOrEmail] = useState('');
  const [password, setPassword] = useState('');
  const { setUser } = useContext(UserContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login(usernameOrEmail, password);
      const token = response.data.accessToken;
      localStorage.setItem('accessToken', token);

      // Fetch user information after login
      const userResponse = await fetchMe(token);
      const user = userResponse.data;
      setUser(user);

      toast.success('Login successful!');
      navigate('/');
    } catch (err) {
      if (err.response && err.response.data) {
        const errorData = err.response.data;
        if (errorData.timestamp && errorData.message && errorData.details) {
          toast.error(errorData.message);
        } else {
          Object.values(errorData).forEach((errorMessage) => {
            toast.error(errorMessage);
          });
        }
      } else {
        toast.error('Login failed. Please try again.');
      }
    }
  };

  return (
    <div>
      <div className="container login-container">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="usernameOrEmail" className="form-label">Username or Email</label>
            <input 
              type="text" 
              className="form-control" 
              id="usernameOrEmail" 
              value={usernameOrEmail}
              onChange={(e) => setUsernameOrEmail(e.target.value)} 
              required 
            />
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">Password</label>
            <input 
              type="password" 
              className="form-control" 
              id="password" 
              value={password}
              onChange={(e) => setPassword(e.target.value)} 
              required 
            />
          </div>
          <button type="submit" className="btn btn-primary">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
