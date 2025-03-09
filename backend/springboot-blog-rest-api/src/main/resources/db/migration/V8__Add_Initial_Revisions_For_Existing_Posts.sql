-- Thêm revision cho các bài viết cũ
INSERT INTO post_revisions (post_id, user_id, title, content, created_at, revision_type)
SELECT p.id, p.user_id, p.title, p.content, p.last_updated, 'CREATED'
FROM posts p
WHERE NOT EXISTS (
    SELECT 1 FROM post_revisions pr WHERE pr.post_id = p.id
);

-- Thêm tags cho các revision
INSERT INTO post_revision_tags (revision_id, tag_id)
SELECT pr.id, pt.tag_id
FROM post_revisions pr
JOIN posts p ON pr.post_id = p.id
JOIN posts_tags pt ON p.id = pt.post_id
WHERE NOT EXISTS (
    SELECT 1 FROM post_revision_tags prt WHERE prt.revision_id = pr.id AND prt.tag_id = pt.tag_id
); 