CREATE TABLE IF NOT EXISTS post_revisions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    title VARCHAR(80) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    revision_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_post_revisions_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT fk_post_revisions_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS post_revision_tags (
    revision_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (revision_id, tag_id),
    CONSTRAINT fk_post_revision_tags_revision FOREIGN KEY (revision_id) REFERENCES post_revisions(id) ON DELETE CASCADE,
    CONSTRAINT fk_post_revision_tags_tag FOREIGN KEY (tag_id) REFERENCES tags(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci; 