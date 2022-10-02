package com.knowledgereplica.model.data;

import com.knowledgereplica.model.CategoryEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PostData implements Serializable {
    @NotEmpty(message = "{post.validation.title}")
    private String title;
    @NotEmpty(message = "{post.validation.title}")
    private String description;
    @NotNull(message = "{post.validation.category}")
    private CategoryEntity category;
    private String thumbnail;
    @NotEmpty(message = "{post.validation.content}")
    private String content;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
