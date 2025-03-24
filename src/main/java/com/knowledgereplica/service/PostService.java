package com.knowledgereplica.service;

import com.knowledgereplica.model.CategoryEntity;
import com.knowledgereplica.model.CommentEntity;
import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.model.data.CommentData;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
  List<PostEntity> getAllPost();

  boolean isPostExists(long id);

  boolean isPostExists(PostEntity post);

  List<PostEntity> getAllPost(Pageable pageable);

  PostEntity getPostById(long id);

  PostEntity getPostByIdAndUpdateViews(long id);

  void removePostById(long id);

  void removePost(PostEntity post);

  void savePost(PostEntity post);

  List<PostEntity> getAllPostByUser(UserEntity userEntity);

  List<CommentEntity> getAllCommentsByPost(PostEntity post);

  List<CommentEntity> findAllByPostId(long postId);

  void removeCommentById(long id);

  void removeComment(CommentEntity commentEntity);

  void saveComment(CommentData commentData, PostEntity post);

  List<CategoryEntity> getAllCategory();

  CategoryEntity getCategoryById(String id);

  Page<PostEntity> findPaginated(Pageable pageable, List<PostEntity> postEntityList);

  List<PostEntity> getAllPostByCategory(CategoryEntity categoryEntity);

  List<PostEntity> getAllPostByCategoryId(String category);

  PostEntity getNextPost(long postId, PostEntity post);

  PostEntity getPreviousPost(long postId, PostEntity post);
}
