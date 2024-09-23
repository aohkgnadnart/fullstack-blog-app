// src/components/UserListContainer.js
import React from 'react';
import UserList from './UserList';
import Pagination from './Pagination';

const UserListContainer = ({ users, pageNo, totalPages, setPageNo }) => {
  return (
    <>
      <UserList users={users} />
      {totalPages > 1 && (
        <Pagination pageNo={pageNo} totalPages={totalPages} setPageNo={setPageNo} />
      )}
    </>
  );
};

export default UserListContainer;
