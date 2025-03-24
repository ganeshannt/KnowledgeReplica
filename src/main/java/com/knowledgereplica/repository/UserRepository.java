package com.knowledgereplica.repository;

import com.knowledgereplica.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  public UserEntity getByEmail(String email);
}
