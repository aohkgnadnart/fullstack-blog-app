import React, { useState, useEffect } from 'react';
import { fetchPostRevisionHistory, fetchPostRevision, compareRevisions, updatePost } from '../apis/postApi';
import { Modal, Button } from 'react-bootstrap';
import { toast } from 'react-toastify';
import './PostRevisionHistory.css';

const PostRevisionHistory = ({ postId, onClose, onRevisionRestored }) => {
  const [revisions, setRevisions] = useState([]);
  const [selectedRevisions, setSelectedRevisions] = useState([]);
  const [comparison, setComparison] = useState('');
  const [showComparisonModal, setShowComparisonModal] = useState(false);
  const [showRestoreModal, setShowRestoreModal] = useState(false);
  const [selectedRevisionForRestore, setSelectedRevisionForRestore] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadRevisions();
  }, [postId]);

  const loadRevisions = async () => {
    try {
      const response = await fetchPostRevisionHistory(postId);
      setRevisions(response.data.content);
      setLoading(false);
    } catch (error) {
      setError('Error loading revision history');
      setLoading(false);
    }
  };

  const handleRevisionSelect = (revision) => {
    if (selectedRevisions.includes(revision)) {
      setSelectedRevisions(selectedRevisions.filter(r => r !== revision));
    } else if (selectedRevisions.length < 2) {
      setSelectedRevisions([...selectedRevisions, revision]);
    }
  };

  const handleCompare = async () => {
    if (selectedRevisions.length !== 2) return;

    try {
      const response = await compareRevisions(
        postId,
        selectedRevisions[0].id,
        selectedRevisions[1].id
      );
      
      // Xử lý và định dạng kết quả so sánh
      const formattedComparison = response.data.split('\n').map(line => {
        if (line.startsWith('+')) {
          return `<span class="added">${line}</span>`;
        } else if (line.startsWith('-')) {
          return `<span class="removed">${line}</span>`;
        }
        return line;
      }).join('\n');

      setComparison(formattedComparison);
      setShowComparisonModal(true);
    } catch (error) {
      setError('Error comparing revisions');
    }
  };

  const handleRestoreClick = (revision) => {
    setSelectedRevisionForRestore(revision);
    setShowRestoreModal(true);
  };

  const handleRestore = async () => {
    try {
      if (!selectedRevisionForRestore) return;

      // Chuyển đổi tags từ object thành mảng tên
      const tagNames = selectedRevisionForRestore.tags.map(tag => ({ name: tag.name }));

      const postData = {
        title: selectedRevisionForRestore.title,
        content: selectedRevisionForRestore.content,
        tags: tagNames
      };

      await updatePost(postId, postData);
      toast.success('Đã hoàn tác về phiên bản trước đó');
      setShowRestoreModal(false);
      if (onRevisionRestored) {
        onRevisionRestored();
      }
      onClose();
    } catch (error) {
      toast.error('Lỗi khi hoàn tác phiên bản');
      console.error('Error restoring revision:', error);
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleString();
  };

  const getRevisionTypeLabel = (type) => {
    switch (type) {
      case 'CREATED':
        return 'Created';
      case 'UPDATED':
        return 'Updated';
      case 'DELETED':
        return 'Deleted';
      default:
        return type;
    }
  };

  if (loading) return <div>Loading revision history...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="revision-history">
      <h3>Revision History</h3>
      <div className="revisions-list">
        {revisions.map((revision) => (
          <div
            key={revision.id}
            className={`revision-item ${selectedRevisions.includes(revision) ? 'selected' : ''}`}
          >
            <div className="revision-header">
              <span className={`revision-type ${revision.revisionType.toLowerCase()}`}>
                {getRevisionTypeLabel(revision.revisionType)}
              </span>
              <span className="revision-date">{formatDate(revision.createdAt)}</span>
            </div>
            <div className="revision-user">
              by {revision.user.username}
            </div>
            {revision.tags && revision.tags.length > 0 && (
              <div className="revision-tags">
                {revision.tags.map(tag => (
                  <span key={tag.id} className="revision-tag">
                    {tag.name}
                  </span>
                ))}
              </div>
            )}
            <div className="revision-item-actions">
              <Button
                variant="outline-primary"
                size="sm"
                onClick={() => handleRevisionSelect(revision)}
              >
                {selectedRevisions.includes(revision) ? 'Deselect' : 'Select'}
              </Button>
              {revision.revisionType !== 'DELETED' && (
                <Button
                  variant="outline-success"
                  size="sm"
                  onClick={() => handleRestoreClick(revision)}
                >
                  Restore
                </Button>
              )}
            </div>
          </div>
        ))}
      </div>

      <div className="revision-actions">
        <Button
          variant="secondary"
          onClick={onClose}
        >
          Close
        </Button>
        <Button
          variant="primary"
          onClick={handleCompare}
          disabled={selectedRevisions.length !== 2}
        >
          Compare Selected ({selectedRevisions.length}/2)
        </Button>
      </div>

      {/* Modal so sánh phiên bản */}
      <Modal
        show={showComparisonModal}
        onHide={() => setShowComparisonModal(false)}
        size="lg"
      >
        <Modal.Header closeButton>
          <Modal.Title>Revision Comparison</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <pre 
            className="comparison-content"
            dangerouslySetInnerHTML={{ __html: comparison }}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowComparisonModal(false)}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Modal xác nhận hoàn tác */}
      <Modal
        show={showRestoreModal}
        onHide={() => setShowRestoreModal(false)}
      >
        <Modal.Header closeButton>
          <Modal.Title>Xác nhận hoàn tác</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Bạn có chắc chắn muốn hoàn tác về phiên bản này không?
          <br />
          <small className="text-muted">
            Thời gian: {selectedRevisionForRestore && formatDate(selectedRevisionForRestore.createdAt)}
          </small>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowRestoreModal(false)}>
            Hủy
          </Button>
          <Button variant="primary" onClick={handleRestore}>
            Xác nhận
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default PostRevisionHistory; 