package com.knowledgereplica.service;

import com.knowledgereplica.model.TokenEntity;

public interface TokenService {
    TokenEntity createToken();

    void saveToken(TokenEntity tokenEntity);

    void removeToken(TokenEntity tokenEntity);

    void removeByToken(String token);

    TokenEntity findByToken(String token);
}
