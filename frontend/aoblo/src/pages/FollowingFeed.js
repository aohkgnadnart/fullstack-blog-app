// src/pages/FollowingFeed.js
import React, { useEffect, useState } from 'react';
import { fetchFollowingPosts } from '../apis/postApi'; // Import API để lấy bài đăng từ người dùng theo dõi
import PostListContainer from '../components/PostListContainer';
import NavHeader from '../components/Header/NavHeader';
import './FollowingFeed.css'; // Import file CSS nếu cần

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
          <div className="col-md-12 d-flex align-items-center mt-3">
            <label className="me-3">Sort by:</label>
            <div className="form-check form-check-inline d-flex align-items-center me-3">
              <input 
                className="form-check-input" 
                type="radio" 
                name="sort" 
                value="lastUpdated" 
                checked={sortBy === 'lastUpdated'} 
                onChange={() => setSortBy('lastUpdated')} 
              />
              <label className="form-check-label ms-1">Default (last updated)</label>
            </div>
            <div className="form-check form-check-inline d-flex align-items-center me-3">
              <input 
                className="form-check-input" 
                type="radio" 
                name="sort" 
                value="viewerCount" 
                checked={sortBy === 'viewerCount'} 
                onChange={() => setSortBy('viewerCount')} 
              />
              <label className="form-check-label ms-1">Viewer</label>
            </div>
            <div className="form-check form-check-inline d-flex align-items-center me-3">
              <input 
                className="form-check-input" 
                type="radio" 
                name="sort" 
                value="likeCount" 
                checked={sortBy === 'likeCount'} 
                onChange={() => setSortBy('likeCount')} 
              />
              <label className="form-check-label ms-1">Like</label>
            </div>
            <div className="form-check form-check-inline d-flex align-items-center me-3">
              <input 
                className="form-check-input" 
                type="radio" 
                name="sort" 
                value="dislikeCount" 
                checked={sortBy === 'dislikeCount'} 
                onChange={() => setSortBy('dislikeCount')} 
              />
              <label className="form-check-label ms-1">Dislike</label>
            </div>
            <div className="form-check form-check-inline d-flex align-items-center me-3">
              <input 
                className="form-check-input" 
                type="radio" 
                name="sort" 
                value="commentCount" 
                checked={sortBy === 'commentCount'} 
                onChange={() => setSortBy('commentCount')} 
              />
              <label className="form-check-label ms-1">Comment</label>
            </div>
            <div className="form-check form-check-inline d-flex align-items-center">
              <input 
                className="form-check-input" 
                type="checkbox" 
                checked={sortDir === 'desc'}
                onChange={(e) => setSortDir(e.target.checked ? 'desc' : 'asc')}
              />
              <label className="form-check-label ms-1">Descending</label>
            </div>
          </div>
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
