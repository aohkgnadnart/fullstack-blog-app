import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { UserContext } from '../App';

const PrivateRoute = ({ element, requiredRole }) => {
  const { user } = useContext(UserContext);

  if (!user) {
    return <Navigate to="/login" />;
  }

  if (requiredRole && !user.roles.some(role => role.name === requiredRole)) {
    return <Navigate to="/" />; // Điều hướng về trang chủ nếu người dùng không có quyền truy cập
  }

  return element;
};

export default PrivateRoute;
