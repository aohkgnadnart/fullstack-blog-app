import React, { useEffect, useState, useCallback } from 'react';
import { debounce } from 'lodash';
import { fetchPosts, fetchPostsByTitle, fetchPostsByTag, fetchPostsByUser } from '../apis/postApi';
import { fetchTags } from '../apis/tagApi';
import { fetchTop5UsersByUsername } from '../apis/userApi';
import Header from '../components/Header/Header';
import SearchInput from '../components/SearchInput';
import PostListContainer from '../components/PostListContainer';
import Footer from '../components/Footer';
import 'bootstrap/dist/css/bootstrap.min.css';
import './HomePage.css';
import '../components/Pagination.css';
import NavHeader from '../components/Header/NavHeader';

const HomePage = () => {
  const [posts, setPosts] = useState([]);
  const [defaultPageNo, setDefaultPageNo] = useState(0);
  const [defaultPageSize, setDefaultPageSize] = useState(10);
  const [defaultTotalPages, setDefaultTotalPages] = useState(0);

  const [titlePageNo, setTitlePageNo] = useState(0);
  const [titlePageSize, setTitlePageSize] = useState(10);
  const [titleTotalPages, setTitleTotalPages] = useState(0);

  const [authorPageNo, setAuthorPageNo] = useState(0);
  const [authorPageSize, setAuthorPageSize] = useState(10);
  const [authorTotalPages, setAuthorTotalPages] = useState(0);

  const [tagPageNo, setTagPageNo] = useState(0);
  const [tagPageSize, setTagPageSize] = useState(10);
  const [tagTotalPages, setTagTotalPages] = useState(0);

  const [sortBy, setSortBy] = useState('lastUpdated');
  const [sortDir, setSortDir] = useState('desc');
  const [viewBy, setViewBy] = useState('default');
  const [titleInput, setTitleInput] = useState('');
  const [authorInput, setAuthorInput] = useState('');
  const [tagInput, setTagInput] = useState('');
  const [tags, setTags] = useState([]);
  const [authors, setAuthors] = useState([]);
  const [isTagInputFocused, setIsTagInputFocused] = useState(false);
  const [isAuthorInputFocused, setIsAuthorInputFocused] = useState(false);

  const debouncedFetchPostsByTitle = useCallback(
    debounce((title) => {
      if (title.trim() === '') {
        setPosts([]);
        setTitleTotalPages(0);
        return;
      }
      fetchPostsByTitle(title, titlePageNo, titlePageSize, sortBy, sortDir).then((response) => {
        setPosts(response.data.content);
        setTitleTotalPages(response.data.totalPages);
      }).catch((error) => {
        console.error('Error fetching posts by title', error);
      });
    }, 500),
    [titlePageNo, titlePageSize, sortBy, sortDir]
  );

  const debouncedFetchTags = useCallback(
    debounce((tag) => {
      if (tag.trim() === '') {
        setPosts([]);
        setTagTotalPages(0);
        setTags([]);
        return;
      }
      fetchTags(tag).then((response) => {
        setTags(response.data);
        if (response.data.length >= 1) {
          fetchPostsByTag(response.data[0].id, tagPageNo, tagPageSize, sortBy, sortDir).then((res) => {
            setPosts(res.data.content);
            setTagTotalPages(res.data.totalPages);
          }).catch((error) => {
            console.error('Error fetching posts by tag', error);
          });
        } else {
          setPosts([]);
          setTagTotalPages(0);
        }
      }).catch((error) => {
        console.error('Error fetching tags', error);
      });
    }, 500),
    [tagPageNo, tagPageSize, sortBy, sortDir]
  );

  const debouncedFetchTop5UsersByUsername = useCallback(
    debounce((author) => {
      if (author.trim() === '') {
        setPosts([]);
        setAuthorTotalPages(0);
        setAuthors([]);
        return;
      }
      fetchTop5UsersByUsername(author).then((response) => {
        setAuthors(response.data);
        if (response.data.length >= 1) {
          fetchPostsByUser(response.data[0].id, authorPageNo, authorPageSize, sortBy, sortDir).then((res) => {
            setPosts(res.data.content);
            setAuthorTotalPages(res.data.totalPages);
          }).catch((error) => {
            console.error('Error fetching posts by user', error);
          });
        } else {
          setPosts([]);
          setAuthorTotalPages(0);
        }
      }).catch((error) => {
        console.error('Error fetching users', error);
      });
    }, 500),
    [authorPageNo, authorPageSize, sortBy, sortDir]
  );

  const fetchPostsData = () => {
    if (viewBy === 'default') {
      fetchPosts(defaultPageNo, defaultPageSize, sortBy, sortDir).then((response) => {
        setPosts(response.data.content);
        setDefaultTotalPages(response.data.totalPages);
      }).catch((error) => {
        console.error('Error fetching posts', error);
      });
    } else if (viewBy === 'title' && titleInput.trim() !== '') {
      fetchPostsByTitle(titleInput, titlePageNo, titlePageSize, sortBy, sortDir).then((response) => {
        setPosts(response.data.content);
        setTitleTotalPages(response.data.totalPages);
      }).catch((error) => {
        console.error('Error fetching posts by title', error);
      });
    } else if (viewBy === 'tag' && tagInput.trim() !== '') {
      debouncedFetchTags(tagInput);
    } else if (viewBy === 'author' && authorInput.trim() !== '') {
      debouncedFetchTop5UsersByUsername(authorInput);
    }
  };

  useEffect(() => {
    fetchPostsData();
  }, [viewBy]);

  useEffect(() => {
    if (viewBy === 'default' || (viewBy === 'title' && titleInput.trim() !== '')) {
      fetchPostsData();
    } else if (viewBy === 'tag' && tagInput.trim() !== '') {
      debouncedFetchTags(tagInput);
    } else if (viewBy === 'author' && authorInput.trim() !== '') {
      debouncedFetchTop5UsersByUsername(authorInput);
    }
  }, [sortBy, sortDir, defaultPageNo, titlePageNo, tagPageNo, authorPageNo, defaultPageSize, titlePageSize, tagPageSize, authorPageSize]);

  const handleTitleChange = (e) => {
    setTitleInput(e.target.value);
    if (viewBy === 'title') {
      debouncedFetchPostsByTitle(e.target.value);
    }
  };

  const handleTagChange = (e) => {
    setTagInput(e.target.value);
    debouncedFetchTags(e.target.value);
  };

  const handleAuthorChange = (e) => {
    setAuthorInput(e.target.value);
    debouncedFetchTop5UsersByUsername(e.target.value);
  };

  const handleTagSelect = (tag) => {
    setTagInput(tag.name);
    setTags([]);
    fetchPostsByTag(tag.id, tagPageNo, tagPageSize, sortBy, sortDir).then((response) => {
      setPosts(response.data.content);
      setTagTotalPages(response.data.totalPages);
    }).catch((error) => {
      console.error('Error fetching posts by tag', error);
    });
  };

  const handleAuthorSelect = (author) => {
    setAuthorInput(author.username);
    setAuthors([]);
    fetchPostsByUser(author.id, authorPageNo, authorPageSize, sortBy, sortDir).then((response) => {
      setPosts(response.data.content);
      setAuthorTotalPages(response.data.totalPages);
    }).catch((error) => {
      console.error('Error fetching posts by user', error);
    });
  };

  const handleInputClick = (type) => {
    setViewBy(type);
    setPosts([]);
    setDefaultTotalPages(0);
    setTitleTotalPages(0);
    setTagTotalPages(0);
    setAuthorTotalPages(0);
    if (type === 'default') setDefaultPageNo(0);
    else if (type === 'title') setTitlePageNo(0);
    else if (type === 'author') setAuthorPageNo(0);
    else if (type === 'tag') setTagPageNo(0);
  };

  const handleViewByChange = (type) => {
    setViewBy(type);
    setPosts([]);
    setDefaultTotalPages(0);
    setTitleTotalPages(0);
    setTagTotalPages(0);
    setAuthorTotalPages(0);
    if (type === 'default') setDefaultPageNo(0);
    else if (type === 'title') setTitlePageNo(0);
    else if (type === 'author') setAuthorPageNo(0);
    else if (type === 'tag') setTagPageNo(0);
  };

  const handleSortByChange = (sort) => {
    setSortBy(sort);
  };

  const handleSortDirChange = (e) => {
    setSortDir(e.target.checked ? 'desc' : 'asc');
  };

  const isSortByVisible = () => {
    if (viewBy === 'default') return true;
    if (viewBy === 'title' && titleInput.trim() !== '') return true;
    if (viewBy === 'author' && authorInput.trim() !== '') return true;
    if (viewBy === 'tag' && tagInput.trim() !== '') return true;
    return false;
  };

  return (
    <div>
      <NavHeader />
      <div className="container main-content">
        <div className="row my-3">
          <SearchInput 
            viewBy={viewBy} 
            handleViewByChange={handleViewByChange}
            handleInputClick={handleInputClick}
            titleInput={titleInput}
            handleTitleChange={handleTitleChange}
            authorInput={authorInput}
            handleAuthorChange={handleAuthorChange}
            tagInput={tagInput}
            handleTagChange={handleTagChange}
            isTagInputFocused={isTagInputFocused}
            setIsTagInputFocused={setIsTagInputFocused}
            tags={tags}
            handleTagSelect={handleTagSelect}
            isAuthorInputFocused={isAuthorInputFocused}
            setIsAuthorInputFocused={setIsAuthorInputFocused}
            authors={authors}
            handleAuthorSelect={handleAuthorSelect}
          />
          {isSortByVisible() && (
            <div className="col-md-12 d-flex align-items-center mt-3">
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
          )}
        </div>
        <PostListContainer 
          posts={posts} 
          pageNo={viewBy === 'default' ? defaultPageNo : viewBy === 'title' ? titlePageNo : viewBy === 'tag' ? tagPageNo : authorPageNo}
          totalPages={viewBy === 'default' ? defaultTotalPages : viewBy === 'title' ? titleTotalPages : viewBy === 'tag' ? tagTotalPages : authorTotalPages} 
          setPageNo={viewBy === 'default' ? setDefaultPageNo : viewBy === 'title' ? setTitlePageNo : viewBy === 'tag' ? setTagPageNo : setAuthorPageNo} 
        />
      </div>
    </div>
  );
};

export default HomePage;
