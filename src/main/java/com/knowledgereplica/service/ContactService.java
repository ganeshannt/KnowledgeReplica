package com.knowledgereplica.service;

import com.knowledgereplica.model.ContactEntity;
import com.knowledgereplica.model.NewsLetterEntity;
import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.data.ContactData;
import com.knowledgereplica.model.data.NewsLetterData;

import java.util.List;

public interface ContactService {
    void saveContact(ContactData contactData);

    List<ContactEntity> getAllContacts();

    void saveNewsLetter(NewsLetterData newsLetterData);

    List<NewsLetterEntity> getAllNewsLetter();

    String[] getAllNewsLetterEmail();

    void sendPostUpdateEmail(PostEntity post);
}
