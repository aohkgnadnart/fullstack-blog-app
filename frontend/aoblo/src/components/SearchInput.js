import React, { useRef } from 'react';

const SearchInput = ({ viewBy, handleViewByChange, handleInputClick, titleInput, handleTitleChange, authorInput, handleAuthorChange, tagInput, handleTagChange, isTagInputFocused, setIsTagInputFocused, tags, handleTagSelect, isAuthorInputFocused, setIsAuthorInputFocused, authors, handleAuthorSelect }) => {
  const titleInputRef = useRef(null);
  const authorInputRef = useRef(null);
  const tagInputRef = useRef(null);

  return (
    <>
      <div className="col-md-12 d-flex align-items-center flex-wrap mt-3">
        <label className="me-3 label-spacing">View by:</label>
        <div className="form-check form-check-inline d-flex align-items-center me-3">
          <input 
            className="form-check-input" 
            type="radio" 
            name="view" 
            value="default" 
            checked={viewBy === 'default'} 
            onChange={() => handleViewByChange('default')} 
          />
          <label className="form-check-label ms-1">Default(all posts)</label>
        </div>
        <div className="form-check form-check-inline d-flex align-items-center me-3">
          <input 
            className="form-check-input" 
            type="radio" 
            name="view" 
            value="title" 
            checked={viewBy === 'title'} 
            onChange={() => handleViewByChange('title')} 
          />
          <label className="form-check-label ms-1">Title</label>
        </div>
        <div className="form-check form-check-inline d-flex align-items-center me-3">
          <input 
            className="form-check-input" 
            type="radio" 
            name="view" 
            value="author" 
            checked={viewBy === 'author'} 
            onChange={() => handleViewByChange('author')} 
          />
          <label className="form-check-label ms-1">Author</label>
        </div>
        <div className="form-check form-check-inline d-flex align-items-center flex-grow-1 position-relative" style={{ position: 'relative' }}>
          <input 
            className="form-check-input" 
            type="radio" 
            name="view" 
            value="tag" 
            checked={viewBy === 'tag'} 
            onChange={() => handleViewByChange('tag')} 
          />
          <label className="form-check-label ms-1">Tag</label>
        </div>
      </div>
      {viewBy === 'title' && (
        <div className="col-md-12 mt-3">
          <input
            type="text"
            className="form-control"
            placeholder="Enter title"
            value={titleInput}
            ref={titleInputRef}
            onClick={() => handleInputClick('title')}
            onChange={handleTitleChange}
          />
        </div>
      )}
      {viewBy === 'author' && (
        <div className="col-md-12 mt-3 position-relative">
          <input
            type="text"
            className="form-control"
            placeholder="Enter author"
            value={authorInput}
            ref={authorInputRef}
            onClick={() => handleInputClick('author')}
            onChange={handleAuthorChange}
            onFocus={() => setIsAuthorInputFocused(true)}
            onBlur={() => setIsAuthorInputFocused(false)}
          />
          {isAuthorInputFocused && authors.length > 0 && (
            <div className="tags-dropdown">
              {authors.map((author) => (
                <div key={author.id} className="tag-item" onMouseDown={() => handleAuthorSelect(author)}>
                  {author.username}
                </div>
              ))}
            </div>
          )}
        </div>
      )}
      {viewBy === 'tag' && (
        <div className="col-md-12 mt-3 position-relative">
          <input
            type="text"
            className="form-control"
            placeholder="Enter tag"
            value={tagInput}
            ref={tagInputRef}
            onClick={() => handleInputClick('tag')}
            onChange={handleTagChange}
            onFocus={() => setIsTagInputFocused(true)}
            onBlur={() => setIsTagInputFocused(false)}
          />
          {isTagInputFocused && tags.length > 0 && (
            <div className="tags-dropdown">
              {tags.map((tag) => (
                <div key={tag.id} className="tag-item" onMouseDown={() => handleTagSelect(tag)}>
                  {tag.name}
                </div>
              ))}
            </div>
          )}
        </div>
      )}
    </>
  );
};

export default SearchInput;
