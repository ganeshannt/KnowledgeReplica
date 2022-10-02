package com.knowledgereplica.model;

import com.knowledgereplica.constant.NewsLetterStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "newsletter")
public class NewsLetterEntity {
    @Id
    private String email;
    @Enumerated(EnumType.STRING)
    private NewsLetterStatus newsLetterStatus;
    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NewsLetterStatus getNewsLetterStatus() {
        return newsLetterStatus;
    }

    public void setNewsLetterStatus(NewsLetterStatus newsLetterStatus) {
        this.newsLetterStatus = newsLetterStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
