// src/components/SortOptions.js
import React from 'react';

const SortOptions = ({ sortBy, sortDir, handleSortByChange, handleSortDirChange }) => {
  return (
    <div className="col-md-12 d-flex align-items-center flex-wrap mt-3">
      <label className="me-3">Sort by:</label>
      <div className="form-check form-check-inline d-flex align-items-center me-3">
        <input 
          className="form-check-input" 
          type="radio" 
          name="sort" 
          value="lastUpdated" 
          checked={sortBy === 'lastUpdated'} 
          onChange={() => handleSortByChange('lastUpdated')} 
        />
        <label className="form-check-label ms-1">Default(last updated)</label>
      </div>
      <div className="form-check form-check-inline d-flex align-items-center me-3">
        <input 
          className="form-check-input" 
          type="radio" 
          name="sort" 
          value="viewerCount" 
          checked={sortBy === 'viewerCount'} 
          onChange={() => handleSortByChange('viewerCount')} 
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
          onChange={() => handleSortByChange('likeCount')} 
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
          onChange={() => handleSortByChange('dislikeCount')} 
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
          onChange={() => handleSortByChange('commentCount')} 
        />
        <label className="form-check-label ms-1">Comment</label>
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
  );
};

export default SortOptions;
