package com.knowledgereplica.service.implementation;

import com.knowledgereplica.constant.NewsLetterStatus;
import com.knowledgereplica.email.NewsLetterPostUpdateEmail;
import com.knowledgereplica.model.ContactEntity;
import com.knowledgereplica.model.NewsLetterEntity;
import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.data.ContactData;
import com.knowledgereplica.model.data.NewsLetterData;
import com.knowledgereplica.repository.ContactRepository;
import com.knowledgereplica.repository.NewsLetterRepository;
import com.knowledgereplica.service.ContactService;
import com.knowledgereplica.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Objects;

@Service
public class DefaultContactServiceImpl implements ContactService {

    @Autowired
    EmailService emailService;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private NewsLetterRepository newsLetterRepository;
    @Value("${site.base.url}")
    private String baseURL;

    @Override
    public void saveContact(ContactData contactData) {
        if (Objects.isNull(contactData)) {
            return;
        }
        ContactEntity contactEntity = new ContactEntity();
        BeanUtils.copyProperties(contactData, contactEntity);
        contactRepository.save(contactEntity);
    }

    @Override
    public List<ContactEntity> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public void saveNewsLetter(NewsLetterData newsLetterData) {
        NewsLetterEntity newsLetter = new NewsLetterEntity();
        BeanUtils.copyProperties(newsLetterData, newsLetter);
        newsLetter.setNewsLetterStatus(NewsLetterStatus.SUBSCRIBED);
        newsLetterRepository.save(newsLetter);
    }

    @Override
    public List<NewsLetterEntity> getAllNewsLetter() {
        return newsLetterRepository.findAll();
    }

    @Override
    public String[] getAllNewsLetterEmail() {
        List<NewsLetterEntity> newsLetterEntityList = newsLetterRepository.findAll();
        String[] emailList = new String[newsLetterEntityList.size()];
        int count = 0;
        for (NewsLetterEntity newsletter :
                newsLetterEntityList) {
            emailList[count++] = newsletter.getEmail();
        }
        return emailList;
    }

    @Override
    @Async
    public void sendPostUpdateEmail(PostEntity post) {
        NewsLetterPostUpdateEmail newsLetterPostUpdateEmail = new NewsLetterPostUpdateEmail();
        newsLetterPostUpdateEmail.init(post);
        newsLetterPostUpdateEmail.buildPostUrl(baseURL, post.getId());
        try {
            emailService.sendMail(newsLetterPostUpdateEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}