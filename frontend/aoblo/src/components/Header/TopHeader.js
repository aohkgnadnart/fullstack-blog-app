import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { UserContext } from '../../App';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './TopHeader.css';

const TopHeader = () => {
  const { user, setUser } = useContext(UserContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('accessToken');
    toast.success('Logout successful!');
    navigate('/');
  };

  const handleLogoClick = (e) => {
    e.preventDefault();
    window.location.href = '/'; // Tải lại trang chủ
  };

  return (
    <div className="top-header">
      <div className="logo">
        <a href="/" className="nav-link" onClick={handleLogoClick}>AOBLO</a>
      </div>
      <div className="auth-links">
        {user ? (
          <>
            <Link to="/profile" className="nav-link username">{user.username}</Link>
            <button onClick={handleLogout} className="btn btn-link">Logout</button>
          </>
        ) : (
          <>
            <Link to="/login" className="nav-link login-register">Login</Link>  <Link to="/register" className="nav-link login-register">Register</Link>
          </>
        )}
      </div>
    </div>
  );
};

export default TopHeader;
