package com.knowledgereplica.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    private String category;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<PostEntity> posts;

    public CategoryEntity(String category, String name, Set<PostEntity> posts) {
        this.category = category;
        this.name = name;
        this.posts = posts;
    }

    public CategoryEntity() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostEntity> posts) {
        this.posts = posts;
    }

    public int getPostCount() {
        return this.posts.size();
    }
}
