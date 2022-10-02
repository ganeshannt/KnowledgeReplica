package com.knowledgereplica.email;

import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

public class NewsLetterPostUpdateEmail extends AbstractEmailContext {

    @Autowired
    ContactService contactService;

    @Override
    public <T> void init(T context) {
        //we can do any common configuration setup here
        // like setting up some base URL and context
        PostEntity post = (PostEntity) context; // we pass the user information
        put("firstName", "Folks");
        put("author", post.getUser().getFirstName());
        put("postTitle", post.getTitle());
        put("postCategory", post.getCategory().getName());
        setTemplateLocation("email/newsletter-new-post.html");
        setSubject("New Post by " + post.getUser().getFirstName());
        setFrom("authentication@gmail.com");
        String[] emailList = {"ganeshannt@gmail.com", "zealofcs@gmail.com", "ganeshan@gmail.com"};
        setToList(emailList);
    }

    public void buildPostUrl(final String baseURL, final long postId) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/post/" + postId).toUriString();
        put("newPostUpdate", url);
    }
}
