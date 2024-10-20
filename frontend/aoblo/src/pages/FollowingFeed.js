// src/pages/FollowingFeed.js
import React, { useEffect, useState } from 'react';
import { fetchFollowingPosts } from '../apis/postApi'; // Import API để lấy bài đăng từ người dùng theo dõi
import PostListContainer from '../components/PostListContainer';
import NavHeader from '../components/Header/NavHeader';
import './FollowingFeed.css'; // Import file CSS nếu cần
import SortOptions from '../components/SortOptions';


const FollowingFeed = () => {
  const [posts, setPosts] = useState([]);
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [sortBy, setSortBy] = useState('lastUpdated');
  const [sortDir, setSortDir] = useState('desc');

  const fetchPostsData = () => {
    fetchFollowingPosts(pageNo, pageSize, sortBy, sortDir).then((response) => {
      setPosts(response.data.content);
      setTotalPages(response.data.totalPages);
    }).catch((error) => {
      console.error('Error fetching following posts', error);
    });
  };

  useEffect(() => {
    fetchPostsData();
  }, [pageNo, pageSize, sortBy, sortDir]);

  return (
    <div>
      <NavHeader />
      <div className="container main-content">
        <div className="row my-3">
          <SortOptions
            sortBy={sortBy}
            sortDir={sortDir}
            handleSortByChange={setSortBy}
            handleSortDirChange={(e) => setSortDir(e.target.checked ? 'desc' : 'asc')}
          />
        </div>
        <PostListContainer 
          posts={posts} 
          pageNo={pageNo}
          totalPages={totalPages}
          setPageNo={setPageNo} 
        />
      </div>
    </div>
  );
};

export default FollowingFeed;
