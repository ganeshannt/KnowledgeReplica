package com.knowledgereplica.service;

import com.knowledgereplica.exception.EmailNotFoundException;
import com.knowledgereplica.exception.InvalidTokenException;
import com.knowledgereplica.exception.UserAlreadyExistException;
import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.model.data.ResetPasswordData;
import com.knowledgereplica.model.data.UserData;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
  public void register(UserData userData) throws UserAlreadyExistException, MessagingException;

  public boolean checkIfEmailExists(String email);

  public boolean userVerification(String token) throws InvalidTokenException, MessagingException;

  void forgotPassword(ResetPasswordData resetPasswordData) throws MessagingException;

  boolean verifyResetPasswordToken(String token) throws InvalidTokenException, MessagingException;

  void resetPassword(ResetPasswordData resetPasswordData) throws InvalidTokenException;

  UserEntity getUserByEmail(String email) throws EmailNotFoundException;
}
