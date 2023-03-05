package com.knowledgereplica.configuration;

import com.knowledgereplica.service.implementation.CustomUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/", "/post/**", "/contact", "/newsletter", "/blog/**", "/authenticate/**", "/actuator/**", "/h2-console/**", "/media/thumbnail/**", "/media/profile/**", "/resources/**", "/static/**", "/css/**", "/img/**", "/images/**", "/icons-reference/**", "/fonts/**", "/vendor/**", "/js/**").permitAll()
                .requestMatchers("/account/user/**").hasAuthority("USER")
                .requestMatchers("/account/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(userDetailsService())
                .and()
                .formLogin()
                    .loginPage("/authenticate/login")
                    .loginProcessingUrl("/authenticate/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(authSuccessHandler())
                    .failureUrl("/authenticate/login?error=true")
                .and()
                .logout()
                    .logoutSuccessUrl("/authenticate/login")
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .maximumSessions(1)
                    .expiredUrl("/authenticate/login?invalid-session=true");
        return httpSecurity.build();
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DataSource dataSource;

    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    /**
     * We need this bean for the session management. Specially if we want to control the concurrent session-control support
     * with Spring security.
     *
     * @return HttpSessionEventPublisher object
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
