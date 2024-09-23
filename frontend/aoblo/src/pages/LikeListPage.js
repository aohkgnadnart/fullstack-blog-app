// src/pages/LikeListPage.js
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { fetchUsersLikedPost } from '../apis/userApi';
import UserListContainer from '../components/UserListContainer';
import NavHeader from '../components/Header/NavHeader';
import './LikeListPage.css';

const LikeListPage = () => {
  const { postId } = useParams();
  const [users, setUsers] = useState([]);
  const [pageNo, setPageNo] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchUsersData = () => {
    fetchUsersLikedPost(postId, pageNo, 10)
      .then(response => {
        setUsers(response.data.content);
        setTotalPages(response.data.totalPages);
      })
      .catch(error => {
        console.error('Error fetching users who liked the post', error);
      });
  };

  useEffect(() => {
    fetchUsersData();
  }, [postId, pageNo]);

  return (
    <div>
      <NavHeader />
      <div className="container main-content">
        <h1>Users who liked this post</h1>
        <UserListContainer 
          users={users} 
          pageNo={pageNo} 
          totalPages={totalPages} 
          setPageNo={setPageNo} 
        />
      </div>
    </div>
  );
};

export default LikeListPage;
