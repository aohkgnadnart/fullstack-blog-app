// src/components/UserItem.js
import React from 'react';
import { Link } from 'react-router-dom';
import './UserItem.css'; // Thêm một file CSS nếu cần để style link

const UserItem = ({ user }) => {
  return (
    <Link to={`/users/${user.id}`} className="user-item-link">
      <div className="user-item">
        <h5>{user.username}</h5>
        <p>Roles: {user.roles.map(role => role.name).join(', ')}</p>
      </div>
    </Link>
  );
};

export default UserItem;
