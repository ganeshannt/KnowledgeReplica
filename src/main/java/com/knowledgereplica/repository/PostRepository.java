package com.knowledgereplica.repository;

import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
  public List<PostEntity> getByUser(UserEntity userEntity);
}
