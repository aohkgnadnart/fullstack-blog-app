package com.springboot.blog.repository;

import com.springboot.blog.entity.Tag;
import com.springboot.blog.payload.TagResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM tags t WHERE t.name LIKE %:name% ORDER BY t.name ASC LIMIT 5", nativeQuery = true)
    List<Tag> findTop5ByNameContains(@Param("name") String name);
}
