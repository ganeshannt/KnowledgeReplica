package com.knowledgereplica.service.implementation;

import com.knowledgereplica.constant.Provider;
import com.knowledgereplica.constant.Role;
import com.knowledgereplica.email.PasswordResetEmailContext;
import com.knowledgereplica.email.UserVerificationEmailContext;
import com.knowledgereplica.exception.EmailNotFoundException;
import com.knowledgereplica.exception.InvalidTokenException;
import com.knowledgereplica.exception.UserAlreadyExistException;
import com.knowledgereplica.model.TokenEntity;
import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.model.data.ResetPasswordData;
import com.knowledgereplica.model.data.UserData;
import com.knowledgereplica.repository.UserRepository;
import com.knowledgereplica.service.AuthenticationService;
import com.knowledgereplica.service.EmailService;
import com.knowledgereplica.service.TokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.util.Objects;

@Service
public class DefaultAuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${site.base.url}")
    private String baseURL;

    @Override
    public void register(UserData userData) throws UserAlreadyExistException, MessagingException {
        if (checkIfEmailExists(userData.getEmail())) {
            throw new UserAlreadyExistException();
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userData, userEntity);
        userEntity.setRole(Role.USER);
        userEntity.setProvider(Provider.FORM);
        userEntity.setPassword(encodePassword(userEntity.getPassword()));
        userRepository.save(userEntity);
        sendVerificationEmail(userEntity);
    }

    @Override
    public boolean userVerification(String token) throws InvalidTokenException, MessagingException {
        TokenEntity tokenEntity = tokenService.findByToken(token);
        if (Objects.isNull(tokenEntity)) {
            throw new InvalidTokenException();
        }
        if (tokenEntity.isExpired()) {
            sendVerificationEmail(tokenEntity.getUser());
            tokenService.removeByToken(tokenEntity.getToken());
            return false;
        }
        UserEntity userEntity = tokenEntity.getUser();
        userEntity.setVerified(true);
        userRepository.save(userEntity);
        tokenService.removeToken(tokenEntity);
        return true;
    }

    @Override
    public void forgotPassword(ResetPasswordData resetPasswordData) throws MessagingException {
        UserEntity userEntity = userRepository.getByEmail(resetPasswordData.getEmail());
        if (Objects.isNull(userEntity)) {
            System.out.println("Forgot Password - Email not found");
        } else {
            sendResetPasswordEmail(userEntity);
        }
    }

    public boolean verifyResetPasswordToken(String token) throws InvalidTokenException, MessagingException {
        TokenEntity tokenEntity = tokenService.findByToken(token);
        if (Objects.isNull(tokenEntity)) {
            throw new InvalidTokenException();
        }
        if (tokenEntity.isExpired()) {
            sendResetPasswordEmail(tokenEntity.getUser());
            return false;
        }
        return true;
    }

    @Override
    public void resetPassword(ResetPasswordData resetPasswordData) throws InvalidTokenException {
        TokenEntity tokenEntity = tokenService.findByToken(resetPasswordData.getToken());
        if (Objects.isNull(tokenEntity)) {
            throw new InvalidTokenException();
        }
        UserEntity userEntity = tokenEntity.getUser();
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("Reset Password - User not found");
        }
        userEntity.setPassword(encodePassword(resetPasswordData.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(String email) throws EmailNotFoundException {
        UserEntity userEntity = userRepository.getByEmail(email);
        if (Objects.isNull(userEntity)) {
            throw new EmailNotFoundException();
        }
        return userEntity;
    }

    protected void sendVerificationEmail(UserEntity userEntity) throws MessagingException {
        TokenEntity tokenEntity = tokenService.createToken();
        tokenEntity.setUser(userEntity);
        tokenService.saveToken(tokenEntity);
        UserVerificationEmailContext userVerificationEmailContext = new UserVerificationEmailContext();
        userVerificationEmailContext.init(userEntity);
        userVerificationEmailContext.buildVerificationUrl(baseURL, tokenEntity.getToken());
        emailService.sendMail(userVerificationEmailContext);

    }

    protected void sendResetPasswordEmail(UserEntity userEntity) throws MessagingException {
        TokenEntity tokenEntity = tokenService.createToken();
        tokenEntity.setUser(userEntity);
        tokenService.saveToken(tokenEntity);
        PasswordResetEmailContext passwordResetEmailContext = new PasswordResetEmailContext();
        passwordResetEmailContext.init(userEntity);
        passwordResetEmailContext.buildVerificationUrl(baseURL, tokenEntity.getToken());
        emailService.sendMail(passwordResetEmailContext);
    }

    protected String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        UserEntity userEntity = userRepository.getByEmail(email);
        return !Objects.isNull(userEntity);
    }
}
