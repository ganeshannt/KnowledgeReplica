package com.knowledgereplica.repository;

import com.knowledgereplica.model.CommentEntity;
import com.knowledgereplica.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    public List<CommentEntity> getByPost(PostEntity post);

    @Query(nativeQuery = true, value = "SELECT * FROM COMMENT WHERE POST_ID=:postId")
    public List<CommentEntity> getByPostId(long postId);
}
