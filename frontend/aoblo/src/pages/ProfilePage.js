import React, { useContext, useEffect, useState } from 'react';
import { UserContext } from '../App';
import { fetchUserById, fetchFollowers, fetchFollowing, fetchFriends, fetchReceivedFriendRequests, fetchSentFriendRequests, downgradeFromAdmin } from '../apis/userApi';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import UserListContainer from '../components/UserListContainer';
import { Modal, Button } from 'react-bootstrap';
import './ProfilePage.css';

const ProfilePage = () => {
  const { user, setUser } = useContext(UserContext);
  const [profileData, setProfileData] = useState(null);
  const [users, setUsers] = useState([]);
  const [showUsers, setShowUsers] = useState(false);
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [sortBy, setSortBy] = useState('username');
  const [sortDir, setSortDir] = useState('asc');
  const [activeTab, setActiveTab] = useState('');
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (user) {
      fetchUserById(user.id)
        .then(response => {
          setProfileData(response.data);
        })
        .catch(error => {
          toast.error('Error fetching profile data');
          console.error('Error fetching profile data', error);
        });
    }
  }, [user]);

  const handleChangePassword = () => {
    navigate('/change-password');
  };

  const handleFetchData = (fetchFunction, tabName) => {
    setActiveTab(tabName);
    setShowUsers(true);
    fetchFunction(pageNo, pageSize, sortBy, sortDir)
      .then(response => {
        setUsers(response.data.content);
        setTotalPages(response.data.totalPages);
      })
      .catch(error => {
        toast.error(`Error fetching ${tabName}`);
        console.error(`Error fetching ${tabName}`, error);
      });
  };

  const handleDowngrade = () => {
    downgradeFromAdmin()
      .then(() => {
        toast.success('User downgraded successfully');
        setShowModal(false);
        const updatedUser = { ...user, roles: user.roles.filter(role => role.name !== 'ROLE_ADMIN') };
        setUser(updatedUser);
      })
      .catch(error => {
        toast.error('Error downgrading user');
        console.error('Error downgrading user', error);
      });
  };

  const handleDowngradeClick = () => {
    setShowModal(true);
  };

  const handleClose = () => {
    setShowModal(false);
  };

  if (!profileData) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container">
      <div className="profile-header">
        <h1>{profileData.name}</h1>
        <p>@{profileData.username}</p>
        <p>Email: {profileData.email}</p>
        <p>Joined: {new Date(profileData.createAt).toLocaleString()}</p>
        <p>Last Visit: {new Date(profileData.lastVisit).toLocaleString()}</p>
        <p>Role: {profileData.roles.map(role => role.name).join(', ')}</p>
      </div>
      <div className="profile-actions">
        <button className="btn btn-primary" onClick={handleChangePassword}>Change Password</button>
        {user && user.roles.some(role => role.name === 'ROLE_ADMIN') && (
          <button className="btn btn-danger" onClick={handleDowngradeClick}>Downgrade From Admin</button>
        )}
      </div>
      <div className="profile-navigation">
        <button 
          className="btn btn-secondary" 
          onClick={() => handleFetchData(fetchFollowers, 'Followers')}
        >
          Followers
        </button>
        <button 
          className="btn btn-secondary" 
          onClick={() => handleFetchData(fetchFollowing, 'Following')}
        >
          Following
        </button>
        <button 
          className="btn btn-secondary" 
          onClick={() => handleFetchData(fetchFriends, 'Friends')}
        >
          Friends
        </button>
        <button 
          className="btn btn-secondary" 
          onClick={() => handleFetchData(fetchReceivedFriendRequests, 'Received Friend Requests')}
        >
          Received Friend Requests
        </button>
        <button 
          className="btn btn-secondary" 
          onClick={() => handleFetchData(fetchSentFriendRequests, 'Sent Friend Requests')}
        >
          Sent Friend Requests
        </button>
        <button className="btn btn-secondary">Conversations</button>
      </div>
      {showUsers && (
        <UserListContainer 
          users={users} 
          pageNo={pageNo} 
          totalPages={totalPages} 
          setPageNo={setPageNo} 
        />
      )}

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Xác nhận Downgrade</Modal.Title>
        </Modal.Header>
        <Modal.Body>Bạn có chắc muốn hạ cấp quyền Admin không?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Hủy
          </Button>
          <Button variant="danger" onClick={handleDowngrade}>
            Downgrade
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default ProfilePage;
