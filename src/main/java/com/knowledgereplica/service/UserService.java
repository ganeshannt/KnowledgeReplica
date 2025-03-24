package com.knowledgereplica.service;

import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.model.data.PostData;
import java.util.List;

public interface UserService {
  void createPost(PostData postData, UserEntity userEntity);

  List<PostEntity> getAllPostByUser(UserEntity userEntity);
}
