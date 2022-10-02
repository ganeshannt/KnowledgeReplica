package com.knowledgereplica.service;

import com.knowledgereplica.email.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendMail(AbstractEmailContext context) throws MessagingException;
}
