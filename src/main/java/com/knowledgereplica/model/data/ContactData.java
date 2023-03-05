package com.knowledgereplica.model.data;

import com.knowledgereplica.model.UserEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ContactData implements Serializable {
    @NotEmpty(message = "{contact.validation.firstname}")
    private String firstName;
    @NotEmpty(message = "{contact.validation.lastname}")
    private String lastName;
    @NotEmpty(message = "{contact.validation.email}")
    @Email
    private String email;
    @NotEmpty(message = "{contact.validation.message}")
    private String message;
    private UserEntity userEntity;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
