package com.knowledgereplica.service.implementation;

import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getByEmail(email);
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException(email);
        }
        boolean isEnabled = userEntity.isVerified();
        String role = userEntity.getRole().toString();
        UserDetails userDetails = User.withUsername(email)
                .password(userEntity.getPassword())
                .disabled(!isEnabled)
                .authorities(role)
                .build();
        return userDetails;
    }
}
