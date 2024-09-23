import React from 'react';
import PostList from './PostList';
import Pagination from './Pagination';

const PostListContainer = ({ posts, pageNo, totalPages, setPageNo }) => {
  return (
    <>
      <PostList posts={posts} />
      {totalPages > 1 && (
        <Pagination pageNo={pageNo} totalPages={totalPages} setPageNo={setPageNo} />
      )}
    </>
  );
};

export default PostListContainer;
