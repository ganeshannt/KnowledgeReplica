package com.knowledgereplica.model.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

public class UserData implements Serializable {
  @NotEmpty(message = "{registration.validation.firstname}")
  private String firstName;

  @NotEmpty(message = "{registration.validation.lastname}")
  private String lastName;

  @NotEmpty(message = "{registration.validation.email}")
  @Email
  private String email;

  @NotEmpty(message = "{registration.validation.password}")
  private String password;

  @NotEmpty(message = "{registration.validation.password}")
  private String confirmPassword;

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public Boolean isPasswordMatches() {
    return password.contentEquals(confirmPassword);
  }
}
