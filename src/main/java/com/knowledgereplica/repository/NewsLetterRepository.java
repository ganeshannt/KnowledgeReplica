package com.knowledgereplica.repository;

import com.knowledgereplica.model.NewsLetterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsLetterRepository extends JpaRepository<NewsLetterEntity, String> {

    @Query(nativeQuery = true, value = "SELECT EMAIL FROM NEWSLETTER")
    public String[] getAllEmail();
}
