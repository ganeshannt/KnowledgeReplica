package com.knowledgereplica.email;

import com.knowledgereplica.model.UserEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class UserVerificationEmailContext extends AbstractEmailContext {
  private String token;

  @Override
  public <T> void init(T context) {
    // we can do any common configuration setup here
    // like setting up some base URL and context
    UserEntity user = (UserEntity) context; // we pass the user information
    put("firstName", user.getFirstName());
    setTemplateLocation("email/email-verification.html");
    setSubject("Complete your registration");
    setFrom("authentication@gmail.com");
    setTo(user.getEmail());
  }

  public void setToken(String token) {
    this.token = token;
    put("token", token);
  }

  public void buildVerificationUrl(final String baseURL, final String token) {
    final String url =
        UriComponentsBuilder.fromHttpUrl(baseURL)
            .path("/authenticate/signup/verify")
            .queryParam("token", token)
            .toUriString();
    put("verificationURL", url);
  }
}
