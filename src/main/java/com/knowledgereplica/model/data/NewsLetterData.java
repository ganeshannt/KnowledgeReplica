package com.knowledgereplica.model.data;

import com.knowledgereplica.constant.NewsLetterStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

public class NewsLetterData implements Serializable {
    @NotEmpty(message = "{contact.validation.email}")
    @Email
    private String email;
    private NewsLetterStatus newsLetterStatus;

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
}
