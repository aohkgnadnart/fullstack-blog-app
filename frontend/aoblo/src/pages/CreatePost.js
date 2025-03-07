import React, { useState, useCallback, useContext } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { debounce } from 'lodash';
import { createPost } from '../apis/postApi';
import { fetchTags } from '../apis/tagApi';
import TopHeader from '../components/Header/TopHeader';
import { useNavigate } from 'react-router-dom';
import MdEditor from 'react-markdown-editor-lite';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import 'react-markdown-editor-lite/lib/index.css';
import './CreatePost.css';
import { ErrorContext } from '../context/ErrorProvider'; ///////////////

const CreatePost = () => {

  const errors = useContext(ErrorContext); /////////

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [tagInput, setTagInput] = useState('');
  const [tags, setTags] = useState([]);
  const [selectedTags, setSelectedTags] = useState([]);
  const [isTagInputFocused, setIsTagInputFocused] = useState(false);
  const navigate = useNavigate();

  const debouncedFetchTags = useCallback(
    debounce((tag) => {
      fetchTags(tag).then((response) => {
        setTags(response.data);
      }).catch((error) => {
        console.error('Error fetching tags', error);
      });
    }, 500),
    []
  );

  const handleTagChange = (e) => {
    setTagInput(e.target.value);
    debouncedFetchTags(e.target.value);
  };

  const handleTagSelect = (tag) => {
    if (selectedTags.length >= 5) {
      toast.error(errors['ERR_TAG_COUNT'] || 'You can only select up to 5 tags.');
      return;
    }
    if (selectedTags.some(selectedTag => selectedTag.name === tag.name)) {
      toast.error(errors['TAG_ALREADY_SELECTED'] || 'Tag already selected.');
      return;
    }
    setSelectedTags([...selectedTags, tag]);
    setTagInput('');
    setTags([]);
  };

  const handleTagRemove = (tag) => {
    setSelectedTags(selectedTags.filter(t => t.id !== tag.id));
  };

  const handleTagKeyDown = (e) => {
    if (e.key === 'Enter' || e.key === ',' || (e.key === ' ' && tagInput.endsWith(','))) {
      e.preventDefault();
      let newTagInput = tagInput.trim();
      if (newTagInput.endsWith(',')) {
        newTagInput = newTagInput.slice(0, -1);
      }
      if (newTagInput) {
        if (selectedTags.some(tag => tag.name === newTagInput)) {
          toast.error(errors['TAG_ALREADY_SELECTED'] || 'Tag already selected.');
        } else {
          const newTag = { id: Date.now(), name: newTagInput };
          handleTagSelect(newTag);
        }
      }
    }
  };

  const handleCreatePost = async () => {
    if (selectedTags.length < 1 || selectedTags.length > 5) {
      toast.error(errors['ERR_TAG_COUNT'] || 'The number of tags must be between 1 and 5.');
      return;
    }

    const post = { title, content, tags: selectedTags.map(tag => tag.name) };
    try {
      await createPost(post);
      toast.success(errors['CREATE_POST_SUCCESS'] || 'Post created successfully!');
      navigate('/');
    } catch (error) {
      if (error.response) {
        const { data } = error.response;
        if (data.message && data.timestamp && data.details) {
          toast.error(data.message);
        } else {
          Object.values(data).forEach(errMsg => {
            toast.error(errMsg);
          });
        }
      } else {
        toast.error(errors['CONNECT_ERROR'] || 'An error occurred while creating the post.');
      }
    }
  };

  return (
    <div>
      <div className="container mt-5">
        <h2>Create Post</h2>
        <div className="form-group">
          <label>Title</label>
          <input
            type="text"
            className="form-control"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Content</label>
          <MdEditor
            value={content}
            style={{ height: '500px' }}
            renderHTML={(text) => <ReactMarkdown remarkPlugins={[remarkGfm]}>{text}</ReactMarkdown>}
            onChange={({ text }) => setContent(text)}
          />
        </div>
        <div className="form-group position-relative">
          <label>Tags</label>
          <input
            type="text"
            className="form-control"
            value={tagInput}
            onChange={handleTagChange}
            onKeyDown={handleTagKeyDown}
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
        <div className="selected-tags mt-2">
          {selectedTags.map(tag => (
            <div key={tag.id} className="tag-badge">
              {tag.name}
              <button type="button" className="close" onClick={() => handleTagRemove(tag)}>
                &times;
              </button>
            </div>
          ))}
        </div>
        <button onClick={handleCreatePost} className="btn btn-primary mt-3">
          Create Post
        </button>
      </div>
    </div>
  );
};

export default CreatePost;
