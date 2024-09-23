import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../App'; // Import UserContext
import './PostItem.css';

const PostItem = ({ post }) => {
  const { user } = useContext(UserContext); // Lấy thông tin user từ context

  return (
    <div className="post-item">
      <div className="post-header d-flex justify-content-between align-items-center">
        <span className="post-author">
          Author: <Link to={`/users/${post.user.id}`} className="post-author-link">{post.user.username}</Link>
        </span>
        <span className="post-updated">Last updated: {new Date(post.lastUpdated).toLocaleString()}</span>
      </div>
      <div className="post-title">
        <Link to={`/posts/${post.id}`}><h3>{post.title}</h3></Link>
      </div>
      <div className="post-tags">
        {post.tags.map(tag => (
          <span key={tag.id} className="badge bg-secondary me-1">{tag.name}</span>
        ))}
      </div>
      <div className="post-footer">
        <span>viewer: {post.viewerCount}</span>
        <span>
          like: 
          {user && user.roles.some(role => role.name === 'ROLE_ADMIN') ? (
            <Link to={`/posts/${post.id}/likes`} className="post-like-link ms-2">
              {post.likeCount}
            </Link>
          ) : (
            <span>{post.likeCount}</span>
          )}
        </span>
        <span>dislike: {post.dislikeCount}</span>
        <span>comment: {post.commentCount}</span>
      </div>
    </div>
  );
};

export default PostItem;
