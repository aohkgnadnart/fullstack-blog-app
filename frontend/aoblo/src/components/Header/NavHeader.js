import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import CreatePostButton from '../CreatePostButton';
import { UserContext } from '../../App'; // Import UserContext
import './NavHeader.css';

const NavHeader = () => {
  const { user } = useContext(UserContext); // Lấy thông tin người dùng từ context

  const isAdmin = user?.roles.some(role => role.name === 'ROLE_ADMIN');

  return (
    <div className="nav-header d-flex justify-content-between align-items-center">
      <div className="nav-links">
        <Link to="/" className="nav-link">Home</Link>
        {user && <Link to="/following-feed" className="nav-link">Following Feed</Link>}
        {isAdmin && <Link to="/all-users" className="nav-link">All Users</Link>} {/* Chỉ hiển thị khi người dùng có vai trò ADMIN */}
        <Link to="/about" className="nav-link">About</Link>
      </div>
      <div className="create-post-button">
        <CreatePostButton />
      </div>
    </div>
  );
};

export default NavHeader;
