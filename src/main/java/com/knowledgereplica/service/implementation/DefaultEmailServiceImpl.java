package com.knowledgereplica.service.implementation;

import com.knowledgereplica.email.AbstractEmailContext;
import com.knowledgereplica.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.util.ArrayUtils;

import java.nio.charset.StandardCharsets;

@Service
public class DefaultEmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    @Override
    public void sendMail(AbstractEmailContext email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getContext());
        String emailContext = springTemplateEngine.process(email.getTemplateLocation(), context);
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setText(emailContext, true);
//      Condition to check whether it is bulk email or single mail context
        try {
            if (ArrayUtils.isEmpty(email.getToList())) {
                mimeMessageHelper.setTo(email.getTo());
                javaMailSender.send(mimeMessage);
            } else {
                for (String singleEmail :
                        email.getToList()) {
                    mimeMessageHelper.setTo(singleEmail);
                    javaMailSender.send(mimeMessage);
                }
            }
        } catch (Exception e) {
            throw new MessagingException();
        }
    }
}
