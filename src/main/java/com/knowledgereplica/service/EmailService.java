package com.knowledgereplica.service;

import com.knowledgereplica.email.AbstractEmailContext;

import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendMail(AbstractEmailContext context) throws MessagingException;
}
