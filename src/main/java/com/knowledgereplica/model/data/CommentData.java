package com.knowledgereplica.model.data;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CommentData implements Serializable {

    @NotEmpty(message = "{comment.validation.name}")
    private String name;
    @NotEmpty(message = "{comment.validation.comment}")
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
