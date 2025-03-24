package com.knowledgereplica.service.implementation;

import com.knowledgereplica.model.TokenEntity;
import com.knowledgereplica.repository.TokenRepository;
import com.knowledgereplica.service.TokenService;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

@Service
public class DefaultTokenServiceImpl implements TokenService {

  private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
  private static final Charset US_ASCII = StandardCharsets.US_ASCII;
  @Autowired private TokenRepository tokenRepository;

  @Value("${email.token.validity}")
  private long emailLinkExpireTimeInSecond;

  @Override
  public TokenEntity createToken() {
    String tokenValue =
        new String(Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey()));
    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setToken(tokenValue);
    tokenEntity.setCreatedAt(LocalDateTime.now());
    tokenEntity.setExpireAt(LocalDateTime.now().plusSeconds(emailLinkExpireTimeInSecond));
    return tokenEntity;
  }

  @Override
  public void saveToken(TokenEntity tokenEntity) {
    tokenRepository.save(tokenEntity);
  }

  @Override
  public void removeToken(TokenEntity tokenEntity) {
    tokenRepository.delete(tokenEntity);
  }

  @Override
  public void removeByToken(String token) {
    tokenRepository.deleteByToken(token);
  }

  @Override
  public TokenEntity findByToken(String token) {
    return tokenRepository.getByToken(token);
  }
}
