package com.knowledgereplica.service.implementation;

import com.knowledgereplica.model.CategoryEntity;
import com.knowledgereplica.model.CommentEntity;
import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.model.data.CommentData;
import com.knowledgereplica.repository.CategoryRepository;
import com.knowledgereplica.repository.CommentRepository;
import com.knowledgereplica.repository.PostRepository;
import com.knowledgereplica.service.ContactService;
import com.knowledgereplica.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class DefaultPostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ContactService contactService;

    @Override
    public List<PostEntity> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public boolean isPostExists(long id) {
        return postRepository.existsById(id);
    }

    //    TODO:Implement isPostExists by using PostEntity object
    @Override
    public boolean isPostExists(PostEntity post) {
        return false;
    }

    @Override
    public List<PostEntity> getAllPost(Pageable pageable) {
        Page<PostEntity> postEntityPage = postRepository.findAll(pageable);
        return postEntityPage.getContent();
    }

    @Override
    public PostEntity getPostByIdAndUpdateViews(long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow();
        postEntity.setViews(postEntity.getViews() + 1);
        postRepository.save(postEntity);
        return postEntity;
    }

    @Override
    public PostEntity getPostById(long id) {
        return postRepository.getById(id);
    }

    @Override
    public void removePostById(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void removePost(PostEntity post) {
        postRepository.delete(post);
    }

    @Override
    public void savePost(PostEntity post) {
        postRepository.save(post);
        contactService.sendPostUpdateEmail(post);
    }

    @Override
    public List<PostEntity> getAllPostByUser(UserEntity userEntity) {
        return postRepository.getByUser(userEntity);
    }

    @Override
    public List<CommentEntity> getAllCommentsByPost(PostEntity post) {
        return commentRepository.getByPost(post);
    }

    @Override
    public List<CommentEntity> findAllByPostId(long postId) {
        return commentRepository.getByPostId(postId);
    }

    @Override
    public void removeCommentById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void removeComment(CommentEntity commentEntity) {
        commentRepository.delete(commentEntity);
    }

    @Override
    public void saveComment(CommentData commentData, PostEntity post) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentData, commentEntity);
        commentEntity.setPost(post);
        commentRepository.save(commentEntity);
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getCategoryById(String id) {
        return categoryRepository.getById(id.toUpperCase());
    }


    @Override
    public Page<PostEntity> findPaginated(Pageable pageable, List<PostEntity> postEntityList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<PostEntity> list;

        if (postEntityList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, postEntityList.size());
            list = postEntityList.subList(startItem, toIndex);
        }
        Page<PostEntity> postList = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), postEntityList.size());
        return postList;
    }

    @Override
    public List<PostEntity> getAllPostByCategory(CategoryEntity categoryEntity) {
        Set<PostEntity> postEntitySet = categoryEntity.getPosts();
        return new ArrayList<>(postEntitySet);
    }

    @Override
    public List<PostEntity> getAllPostByCategoryId(String category) {
        CategoryEntity categoryEntity = getCategoryById(category);
        Set<PostEntity> postEntitySet = categoryEntity.getPosts();
        return new ArrayList<>(postEntitySet);
    }

    @Override
    public PostEntity getNextPost(long postId, PostEntity post) {
        return (isPostExists(postId + 1)) ? getPostById(post.getId() + 1) : null;
    }

    @Override
    public PostEntity getPreviousPost(long postId, PostEntity post) {
        return (isPostExists(postId - 1)) ? getPostById(post.getId() - 1) : null;
    }

}