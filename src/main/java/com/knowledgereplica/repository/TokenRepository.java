package com.knowledgereplica.repository;

import com.knowledgereplica.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
  public void deleteByToken(String token);

  public TokenEntity getByToken(String token);
}
