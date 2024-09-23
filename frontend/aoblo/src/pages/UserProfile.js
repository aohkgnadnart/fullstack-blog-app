import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchUserById, checkIfFollowing, followUser, unfollowUser, checkIfFriend, sendFriendRequest, unfriendUser, upgradeToAdmin } from '../apis/userApi';
import { UserContext } from '../App';
import { toast } from 'react-toastify';
import { Modal, Button } from 'react-bootstrap'; // Import Modal and Button
import './UserProfile.css';

const UserProfile = () => {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [isFollowing, setIsFollowing] = useState(false);
  const [isFriend, setIsFriend] = useState(false);
  const [showUpgradeModal, setShowUpgradeModal] = useState(false); // State to handle Modal
  const navigate = useNavigate();
  const { user: currentUser } = useContext(UserContext);

  useEffect(() => {
    if (currentUser && currentUser.id === parseInt(id, 10)) {
      navigate('/profile');
    } else {
      fetchUserById(id).then(response => {
        setUser(response.data);
      }).catch(error => {
        console.error('Error fetching user profile', error);
      });
    }
  }, [id, currentUser, navigate]);

  useEffect(() => {
    if (currentUser && user) {
      checkIfFollowing(id)
        .then(response => {
          setIsFollowing(response.data);
        })
        .catch(error => {
          console.error('Error checking follow status', error);
        });

      checkIfFriend(id)
        .then(response => {
          setIsFriend(response.data);
        })
        .catch(error => {
          console.error('Error checking friend status', error);
        });
    }
  }, [currentUser, user, id]);

  const handleFollowToggle = () => {
    if (!currentUser) {
      toast.warning('Thao tác này cần đăng nhập');
      return;
    }

    if (isFollowing) {
      unfollowUser(id)
        .then(() => {
          toast.success('You have unfollowed successfully');
          setIsFollowing(false);
        })
        .catch(error => {
          toast.error('Error unfollowing user');
          console.error('Error unfollowing user', error);
        });
    } else {
      followUser(id)
        .then(() => {
          toast.success('You are now following this user');
          setIsFollowing(true);
        })
        .catch(error => {
          toast.error('Error following user');
          console.error('Error following user', error);
        });
    }
  };

  const handleFriendToggle = () => {
    if (!currentUser) {
      toast.warning('Thao tác này cần đăng nhập');
      return;
    }

    if (isFriend) {
      unfriendUser(id)
        .then(() => {
          toast.success('You have unfriended this user');
          checkIfFriend(id).then(response => setIsFriend(response.data));
        })
        .catch(error => {
          toast.error('Error unfriending user');
          console.error('Error unfriending user', error);
        });
    } else {
      sendFriendRequest(id)
        .then(() => {
          toast.success('Friend request sent successfully');
          checkIfFriend(id).then(response => setIsFriend(response.data));
        })
        .catch(error => {
          toast.error('Error sending friend request');
          console.error('Error sending friend request', error);
        });
    }
  };

  const handleSendMessage = () => {
    if (!currentUser) {
      toast.warning('Thao tác này cần đăng nhập');
      return;
    }
    navigate(`/message/${id}`);
  };

  const handleUpgradeClick = () => {
    setShowUpgradeModal(true);
  };

  const handleUpgradeConfirm = async () => {
    try {
      await upgradeToAdmin(id);
      toast.success('User upgraded to admin successfully');
      setShowUpgradeModal(false);
      setUser({ ...user, roles: [...user.roles, { name: 'ROLE_ADMIN' }] });
    } catch (error) {
      toast.error('Error upgrading user');
      console.error('Error upgrading user', error);
    }
  };

  const handleCloseUpgradeModal = () => {
    setShowUpgradeModal(false);
  };

  return user ? (
    <div className="container">
      <div className="profile-header">
        <h1>{user.name}</h1>
        <p>@{user.username}</p>
        <p>Email: {user.email}</p>
        <p>Joined: {new Date(user.createAt).toLocaleString()}</p>
        <p>Last Visit: {new Date(user.lastVisit).toLocaleString()}</p>
        <p>Role: {user.roles.map(role => role.name).join(', ')}</p>
      </div>
      <div className="profile-actions">
        <button
          className={`btn ${isFollowing ? 'btn-danger' : 'btn-primary'}`}
          onClick={handleFollowToggle}
        >
          {isFollowing ? 'Unfollow' : 'Follow'}
        </button>
        <button
          className={`btn ${isFriend ? 'btn-danger' : 'btn-secondary'}`}
          onClick={handleFriendToggle}
        >
          {isFriend ? 'Unfriend' : 'Add Friend'}
        </button>
        <button className="btn btn-info" onClick={handleSendMessage}>Message</button>
        {currentUser && currentUser.roles.some(role => role.name === 'ROLE_ADMIN') && 
          user.roles.every(role => role.name === 'ROLE_USER') && (
          <button className="btn btn-warning" onClick={handleUpgradeClick}>Upgrade To Admin</button>
        )}
      </div>

      {/* Modal xác nhận upgrade */}
      <Modal show={showUpgradeModal} onHide={handleCloseUpgradeModal}>
        <Modal.Header closeButton>
          <Modal.Title>Xác nhận Upgrade</Modal.Title>
        </Modal.Header>
        <Modal.Body>Bạn có chắc muốn nâng cấp người dùng này thành Admin không?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseUpgradeModal}>
            Hủy
          </Button>
          <Button variant="warning" onClick={handleUpgradeConfirm}>
            Upgrade
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  ) : (
    <div>Loading...</div>
  );
};

export default UserProfile;
