// src/pages/AllUsers.js
import React, { useEffect, useState } from 'react';
import { fetchAllUsers } from '../apis/userApi';
import UserListContainer from '../components/UserListContainer';
import NavHeader from '../components/Header/NavHeader';
import './AllUsers.css';

const AllUsers = () => {
  const [users, setUsers] = useState([]);
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [sortBy, setSortBy] = useState('username');
  const [sortDir, setSortDir] = useState('asc');

  const fetchUsersData = () => {
    fetchAllUsers(pageNo, pageSize, sortBy, sortDir)
      .then(response => {
        setUsers(response.data.content);
        setTotalPages(response.data.totalPages);
      })
      .catch(error => {
        console.error('Error fetching users', error);
      });
  };

  useEffect(() => {
    fetchUsersData();
  }, [pageNo, pageSize, sortBy, sortDir]);

  const handleSortByChange = (sort) => {
    setSortBy(sort);
  };

  const handleSortDirChange = (e) => {
    setSortDir(e.target.checked ? 'desc' : 'asc');
  };

  return (
    <div>
      <NavHeader />
      <div className="container main-content">
        <div className="row my-3">
          <div className="col-md-12 d-flex align-items-center mt-3">
            <label className="me-3">Sort by:</label>
            <div className="form-check form-check-inline d-flex align-items-center me-3">
              <input 
                className="form-check-input" 
                type="radio" 
                name="sort" 
                value="username" 
                checked={sortBy === 'username'} 
                onChange={() => handleSortByChange('username')} 
              />
              <label className="form-check-label ms-1">Username</label>
            </div>
            <div className="form-check form-check-inline d-flex align-items-center me-3">
              <input 
                className="form-check-input" 
                type="radio" 
                name="sort" 
                value="roles" 
                checked={sortBy === 'roles'} 
                onChange={() => handleSortByChange('roles')} 
              />
              <label className="form-check-label ms-1">Roles</label>
            </div>
            <div className="form-check form-check-inline d-flex align-items-center">
              <input 
                className="form-check-input" 
                type="checkbox" 
                checked={sortDir === 'desc'}
                onChange={handleSortDirChange}
              />
              <label className="form-check-label ms-1">Descending</label>
            </div>
          </div>
        </div>
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

export default AllUsers;
