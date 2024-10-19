import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import {
  fetchPostById,
  trackPostViewers,
  checkIfLiked,
  checkIfDisliked,
  likePost,
  dislikePost,
  noLikePost,
  noDislikePost,
  deletePost
} from '../apis/postApi';
import { createComment, createReply, deleteComment } from '../apis/commentApi';
import hljs from 'highlight.js';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import remarkSlug from 'remark-slug';
import remarkAutolinkHeadings from 'remark-autolink-headings';
import rehypeHighlight from 'rehype-highlight';
import { unified } from 'unified';
import remarkParse from 'remark-parse';
import { visit } from 'unist-util-visit';
import { toast } from 'react-toastify';
import { Modal, Button } from 'react-bootstrap';
import 'highlight.js/styles/googlecode.css';
import TopHeader from '../components/Header/TopHeader';
import { UserContext } from '../App';
import './PostDetail.css';

const PostDetail = () => {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [replyContent, setReplyContent] = useState('');
  const [replyTo, setReplyTo] = useState(null);
  const [postLiked, setPostLiked] = useState(false);
  const [postDisliked, setPostDisliked] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const { user } = useContext(UserContext);
  const navigate = useNavigate();

  const fetchPostDetails = () => {
    fetchPostById(id).then(response => {
      setPost(response.data);
      setComments(response.data.comments);
    }).catch(error => {
      console.error('Error fetching post', error);
    });
  };

  const [headings, setHeadings] = useState([]);
  

  useEffect(() => {
    if (post) {
      const processor = unified()
        .use(remarkParse)
        .use(remarkSlug);
  
      const tree = processor.parse(post.content);
      processor.runSync(tree);
  
      const headings = [];
  
      visit(tree, 'heading', (node) => {
        if (node.depth >= 1 && node.depth <= 3) {
          const text = node.children
            .filter(child => child.type === 'text' || child.type === 'inlineCode')
            .map(child => child.value)
            .join('');
  
          const id = node.data && node.data.id;
  
          headings.push({
            depth: node.depth,
            value: text,
            id: id
          });
        }
      });
  
      setHeadings(headings);
    }
  }, [post]);
  
  useEffect(() => {
    if (user) {
      trackPostViewers(id).catch(error => console.error('Error tracking viewer', error));

      // Check if post is liked
      checkIfLiked(id).then(response => {
        setPostLiked(response.data);
      }).catch(error => {
        console.error('Error checking if post is liked', error);
      });

      // Check if post is disliked
      checkIfDisliked(id).then(response => {
        setPostDisliked(response.data);
      }).catch(error => {
        console.error('Error checking if post is disliked', error);
      });
    }
  }, [user, id]);

  useEffect(() => {
    fetchPostDetails();
  }, [id]);

  useEffect(() => {
    if (post) {
      document.querySelectorAll('pre code').forEach((block) => {
        hljs.highlightBlock(block);
      });
    }
  }, [post]);

  const handleLike = async () => {
    if (!user) {
      toast.warning('Tính năng này cần đăng nhập');
      return;
    }

    try {
      if (postLiked) {
        await noLikePost(id);
        toast.info('Đã bỏ thích');
      } else {
        await likePost(id);
        toast.success('Đã thích');
      }
      
      setTimeout(() => {
        checkIfLiked(id).then(response => {
          setPostLiked(response.data);
        }).catch(error => {
          console.error('Error checking if post is liked', error);
        });

        checkIfDisliked(id).then(response => {
          setPostDisliked(response.data);
        }).catch(error => {
          console.error('Error checking if post is disliked', error);
        });
      }, 500);
    } catch (error) {
      toast.error('Lỗi khi thực hiện yêu cầu');
      console.error('Error liking/unliking post', error);
    }
  };

  const handleDislike = async () => {
    if (!user) {
      toast.warning('Tính năng này cần đăng nhập');
      return;
    }

    try {
      if (postDisliked) {
        await noDislikePost(id);
        toast.info('Đã bỏ không thích');
      } else {
        await dislikePost(id);
        toast.success('Đã không thích');
      }
      
      setTimeout(() => {
        checkIfLiked(id).then(response => {
          setPostLiked(response.data);
        }).catch(error => {
          console.error('Error checking if post is liked', error);
        });

        checkIfDisliked(id).then(response => {
          setPostDisliked(response.data);
        }).catch(error => {
          console.error('Error checking if post is disliked', error);
        });
      }, 500);
    } catch (error) {
      toast.error('Lỗi khi thực hiện yêu cầu');
      console.error('Error disliking/undisliking post', error);
    }
  };

  const handleCommentSubmit = async () => {
    if (!user) {
      toast.warning('Tính năng này cần đăng nhập');
      return;
    }

    if (newComment.trim() === '') {
      toast.error('Comment phải có nội dung');
      return;
    }

    try {
      await createComment(id, { content: newComment });
      toast.success('Comment đã được thêm');
      fetchPostDetails();
      setNewComment('');
    } catch (error) {
      toast.error('Lỗi khi thêm comment');
      console.error('Error submitting comment', error);
    }
  };

  const handleReplyComment = (commentId) => {
    if (!user) {
      toast.warning('Tính năng này cần đăng nhập');
      return;
    }
    setReplyTo(commentId);
  };

  const handleSubmitReply = async (parentId) => {
    if (!user) {
      toast.warning('Tính năng này cần đăng nhập');
      return;
    }

    if (replyContent.trim() === '') {
      toast.error('Reply phải có nội dung');
      return;
    }

    try {
      await createReply(id, parentId, { content: replyContent });
      toast.success('Reply đã được thêm');
      fetchPostDetails();
      setReplyTo(null);
      setReplyContent('');
    } catch (error) {
      toast.error('Lỗi khi thêm reply');
      console.error('Error submitting reply', error);
    }
  };

  const handleDelete = async () => {
    try {
      await deletePost(id);
      toast.success('Bài viết đã bị xóa');
      navigate('/'); // Điều hướng về trang chủ hoặc trang khác sau khi xóa
    } catch (error) {
      toast.error('Lỗi khi xóa bài viết');
      console.error('Error deleting post', error);
    } finally {
      setShowModal(false);
    }
  };

  const handleDeleteClick = () => {
    setShowModal(true);
  };

  const handleEditClick = () => {
    navigate(`/posts/${id}/edit`); // Điều hướng đến trang chỉnh sửa bài viết
  };

  const handleClose = () => {
    setShowModal(false);
  };

  const handleDeleteComment = async (commentId) => {
    try {
      await deleteComment(commentId);
      toast.success('Comment đã bị xóa');
      fetchPostDetails(); // Load lại danh sách comment sau khi xóa
    } catch (error) {
      toast.error('Lỗi khi xóa comment');
      console.error('Error deleting comment', error);
    }
  };

  const renderComments = (comments, level = 0) => {
    return comments.map(comment => {
      const canDeleteComment = user && (user.roles.some(role => role.name === 'ROLE_ADMIN') || user.id === comment.user.id);

      return (
        <div key={comment.id} className="comment" style={{ marginLeft: level===0?0: 40 }}>
          <div className="comment-header">
            <Link to={`/users/${comment.user.id}`} className="comment-username">{comment.user.username}</Link> | <span>{new Date(comment.createAt).toLocaleString()}</span>
          </div>
          <p className="comment-content">{comment.content}</p>
          <div className="comment-actions">
            <button className="reply" onClick={() => handleReplyComment(comment.id)}>
              Reply
            </button>
            {canDeleteComment && (
              <button
                className="delete-comment" // Sử dụng cùng kiểu với "reply"
                onClick={() => handleDeleteComment(comment.id)}
                style={{ marginLeft: '10px' }}
              >
                Delete
              </button>
            )}
          </div>
          {replyTo === comment.id && (
            <div className="add-comment" style={{ marginLeft: (level + 1) * 20 }}>
              <textarea 
                value={replyContent}
                onChange={(e) => setReplyContent(e.target.value)} 
                placeholder="Add a reply"
              ></textarea>
              <button onClick={() => handleSubmitReply(comment.id)}>Submit</button>
            </div>
          )}
          {comment.replies && renderComments(comment.replies, level + 1)}
        </div>
      );
    });
  };

  const isAuthor = user && post && user.id === post.user.id;
  const isAdminOrAuthor = user && post && (user.roles.some(role => role.name === 'ROLE_ADMIN') || isAuthor);

  return post ? (
    <div>
      <div className="container">
        <div className="post-title">
          <h1>{post.title}</h1>
        </div>
        <div className="post-meta">
          <p>Author: <Link to={`/users/${post.user.id}`} className="post-author-link">{post.user.username}</Link> | Last updated: {new Date(post.lastUpdated).toLocaleString()}</p>
        </div>
        <div className="post-tags">
          {post.tags.map(tag => (
            <span key={tag.id} className="badge bg-secondary me-1">{tag.name}</span>
          ))}
        </div>
        {headings.length > 0 && (
        <div className="table-of-contents">
          <ul>
            {headings.map((heading, index) => (
              <li key={index} style={{ marginLeft: (heading.depth - 1) * 20 }}>
                <a href={`#${heading.id}`}>{heading.value}</a>
              </li>
            ))}
          </ul>
        </div>
      )}

        <ReactMarkdown
          children={post.content}
          remarkPlugins={[remarkGfm, remarkSlug, remarkAutolinkHeadings]}
          rehypePlugins={[rehypeHighlight]}
          className="post-content"
        />
        <div className="post-actions">
          <button
            className={`like ${postLiked ? 'active' : ''}`}
            onClick={handleLike}
          >
            Like
          </button>
          <button
            className={`dislike ${postDisliked ? 'active' : ''}`}
            onClick={handleDislike}
          >
            Dislike
          </button>
          {isAuthor && (
            <button
              className="edit-post"
              style={{ float: 'right', marginLeft: '10px' }}
              onClick={handleEditClick}
            >
              Edit
            </button>
          )}
          {isAdminOrAuthor && (
            <button
              className="delete-post"
              style={{ float: 'right' }}
              onClick={handleDeleteClick}
            >
              Delete
            </button>
          )}
        </div>
        <div className="comments-section">
          <h4>Comments ({post.commentCount})</h4>
          {renderComments(comments)}
          <div className="add-comment">
            <textarea 
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)} 
              placeholder="Add a comment"
            ></textarea>
            <button onClick={handleCommentSubmit}>Submit</button>
          </div>
        </div>
      </div>

      {/* Modal xác nhận xóa */}
      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Xác nhận xóa</Modal.Title>
        </Modal.Header>
        <Modal.Body>Bạn có chắc muốn xóa bài viết này không?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Hủy
          </Button>
          <Button variant="primary" onClick={handleDelete}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  ) : (
    <div>Loading...</div>
  );
};

export default PostDetail;
