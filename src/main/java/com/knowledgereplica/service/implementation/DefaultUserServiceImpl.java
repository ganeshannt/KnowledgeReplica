package com.knowledgereplica.service.implementation;

import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.model.data.PostData;
import com.knowledgereplica.service.PostService;
import com.knowledgereplica.service.UserService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserServiceImpl implements UserService {

  @Autowired PostService postService;

  @Override
  public void createPost(PostData postData, UserEntity userEntity) {
    PostEntity post = new PostEntity();
    BeanUtils.copyProperties(postData, post);
    post.setUser(userEntity);
    postService.savePost(post);
  }

  @Override
  public List<PostEntity> getAllPostByUser(UserEntity userEntity) {
    return postService.getAllPostByUser(userEntity);
  }
}
