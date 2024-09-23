// src/pages/ChangePasswordPage.js
import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { UserContext } from '../App';
import { changePassword } from '../apis/userApi';
import './ChangePasswordPage.css';

const ChangePasswordPage = () => {
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [verifyNewPassword, setVerifyNewPassword] = useState('');
  const { user } = useContext(UserContext);
  const navigate = useNavigate();

  const handleChangePassword = async (e) => {
    e.preventDefault();

    if (newPassword !== verifyNewPassword) {
      toast.error('New password and verify password do not match.');
      return;
    }

    try {
      await changePassword({ currentPassword, newPassword });
      toast.success('Password updated successfully');
      navigate('/profile'); // Redirect to profile page after successful password change
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
        toast.error('Password change failed. Please try again.');
      }
    }
  };

  return (
    <div className="container change-password-container">
      <h2>Change Password</h2>
      <form onSubmit={handleChangePassword}>
        <div className="mb-3">
          <label htmlFor="currentPassword" className="form-label">Current Password</label>
          <input
            type="password"
            className="form-control"
            id="currentPassword"
            value={currentPassword}
            onChange={(e) => setCurrentPassword(e.target.value)}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="newPassword" className="form-label">New Password</label>
          <input
            type="password"
            className="form-control"
            id="newPassword"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="verifyNewPassword" className="form-label">Verify New Password</label>
          <input
            type="password"
            className="form-control"
            id="verifyNewPassword"
            value={verifyNewPassword}
            onChange={(e) => setVerifyNewPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">Change Password</button>
      </form>
    </div>
  );
};

export default ChangePasswordPage;
