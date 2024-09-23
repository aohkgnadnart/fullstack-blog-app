import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Pagination.css';

const Pagination = ({ pageNo, totalPages, setPageNo }) => {
  const [isInputVisible, setIsInputVisible] = useState(false);
  const [inputPage, setInputPage] = useState('');

  const handleInputChange = (e) => {
    const value = e.target.value;
    if (/^\d*$/.test(value)) {
      setInputPage(value);
    }
  };

  const handleInputBlur = () => {
    if (inputPage !== '') {
      const page = parseInt(inputPage, 10) - 1;
      if (page >= 0 && page < totalPages) {
        setPageNo(page);
      }
      setInputPage('');
    }
    setIsInputVisible(false);
  };

  const handlePageJump = (e) => {
    if (e.key === 'Enter' && inputPage !== '') {
      const page = parseInt(inputPage, 10) - 1;
      if (page >= 0 && page < totalPages) {
        setPageNo(page);
        setInputPage('');
        setIsInputVisible(false);
      }
    }
  };

  const renderPageNumbers = () => {
    const pages = [];
    for (let i = 0; i < totalPages; i++) {
      pages.push(
        <button
          key={i}
          className={`btn ${i === pageNo ? 'btn-primary' : 'btn-secondary'} mx-1`}
          onClick={() => setPageNo(i)}
        >
          {i + 1}
        </button>
      );
    }
    return pages;
  };

  return (
    <div className="pagination d-flex justify-content-center my-3">
      <button className="btn btn-secondary me-2" onClick={() => setPageNo(pageNo - 1)} disabled={pageNo === 0}>
        Previous
      </button>
      {renderPageNumbers()}
      <button className="btn btn-secondary ms-2" onClick={() => setPageNo(pageNo + 1)} disabled={pageNo + 1 === totalPages}>
        Next
      </button>
      <button className="btn btn-secondary ms-2" onClick={() => setIsInputVisible(!isInputVisible)}>
        ...
      </button>
      {isInputVisible && (
        <input
          type="text"
          className="form-control ms-2 page-input"
          value={inputPage}
          onChange={handleInputChange}
          onBlur={handleInputBlur}
          onKeyDown={handlePageJump}
          autoFocus
        />
      )}
    </div>
  );
};

export default Pagination;
