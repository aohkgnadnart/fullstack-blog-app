import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../App';
import { Modal, Button } from 'react-bootstrap';
import './CreatePostButton.css';

const CreatePostButton = () => {
  const { user } = useContext(UserContext);
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  const handleCreatePost = () => {
    if (user) {
      navigate('/create-post');
    } else {
      setShowModal(true);
    }
  };

  const handleLogin = () => {
    setShowModal(false);
    navigate('/login');
  };

  const handleClose = () => {
    setShowModal(false);
  };

  return (
    <>
      <button className="btn btn-primary create-post-button" onClick={handleCreatePost}>
        Create Post
      </button>

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Login Required</Modal.Title>
        </Modal.Header>
        <Modal.Body>You need to be logged in to create a post. Do you want to login?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleLogin}>
            Login
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default CreatePostButton;
