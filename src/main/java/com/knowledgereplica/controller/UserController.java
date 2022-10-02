package com.knowledgereplica.controller;

import com.knowledgereplica.exception.EmailNotFoundException;
import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.UserEntity;
import com.knowledgereplica.model.data.PostData;
import com.knowledgereplica.service.AuthenticationService;
import com.knowledgereplica.service.PostService;
import com.knowledgereplica.service.UserService;
import com.knowledgereplica.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/account/user")
public class UserController {

    private static final String CREATE_POST_PAGE = "user/create_post";
    private static final String UPDATE_POST_PAGE = "user/update_post";
    private static final String REDIRECT_CREATE_POST = "redirect:/account/user/post";
    private static final String REDIRECT_USER_HOME = "redirect:/account/user/home";

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    MessageSource messageSource;

    @PostMapping("/post/publish")
    public String createPost(@Valid @ModelAttribute("post") PostData postData, BindingResult bindingResult, @RequestParam("thumbnail") MultipartFile multipartFile, final Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        UserEntity user = null;
        try {
            user = authenticationService.getUserByEmail((String) httpSession.getAttribute("email"));
        } catch (EmailNotFoundException e) {
            return "redirect:/authenticate/login";
        }
        try {
            String filename = UUID.randomUUID().toString() + "." + multipartFile.getContentType().split("/")[1];
            postData.setThumbnail(filename);
            AppUtils.saveFile(filename, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("thumbnailUploadFailed", messageSource.getMessage("post.thumbnail.upload.failed", null, LocaleContextHolder.getLocale()));
            return REDIRECT_CREATE_POST;
        }
        userService.createPost(postData, user);
        redirectAttributes.addFlashAttribute("postCreationSuccess", messageSource.getMessage("post.created", null, LocaleContextHolder.getLocale()));
        return REDIRECT_USER_HOME;
    }

    @GetMapping("/home")
    public String getUserIndexPage(final Model model, HttpServletRequest httpServletRequest) {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        String email = authenticatedUser.getName();
        HttpSession session = httpServletRequest.getSession();
        UserEntity user = null;
        try {
            user = authenticationService.getUserByEmail(email);
        } catch (EmailNotFoundException e) {
            return "redirect:/authenticate/login";
        }
        if (Objects.isNull(session.getAttribute("email"))) {
            session.setAttribute("email", email);
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
            session.setAttribute("userId", user.getId());
        }
        List<PostEntity> postEntityList = userService.getAllPostByUser(user);
        model.addAttribute("postList", postEntityList);
        return "user/home";
    }

    @PostMapping("/post/update/{postId}")
    public String updatePost(@Valid @ModelAttribute("postData") PostData postData, BindingResult bindingResult, @PathVariable("postId") long postId, @RequestParam("thumbnail") MultipartFile multipartFile, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {

        PostEntity postEntity = postService.getPostById(postId);
        postEntity.setTitle(postData.getTitle());
        postEntity.setContent(postData.getContent());
        postEntity.setCategory(postData.getCategory());
        if (!StringUtils.isEmpty(postData.getThumbnail())) {
            postEntity.setThumbnail(postData.getThumbnail());
        } else {
            try {
                AppUtils.deleteFile(postEntity.getThumbnail());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("postUpdateSuccessMessage", messageSource.getMessage("post.updated", null, LocaleContextHolder.getLocale()));
        postService.savePost(postEntity);
        return REDIRECT_USER_HOME;
    }

    @GetMapping("/post/update/{postId}")
    public String getUpdatePostPage(@PathVariable("postId") long postId, final Model model, RedirectAttributes redirectAttributes) {
        PostEntity postUpdate = postService.getPostById(postId);
        if (AppUtils.isValidUserRequest(postUpdate.getUser())) {
            redirectAttributes.addFlashAttribute("userAccessDenied", messageSource.getMessage("user.access.denied", null, LocaleContextHolder.getLocale()));
            return REDIRECT_USER_HOME;
        }
        model.addAttribute("categoryList", postService.getAllCategory());
        model.addAttribute("postUpdate", postUpdate);
        model.addAttribute("postData", new PostData());
        return UPDATE_POST_PAGE;
    }

    @GetMapping("/post/delete/{postId}")
    public String deletePost(@PathVariable("postId") long postId, final Model model, RedirectAttributes redirectAttributes) {
        PostEntity post = postService.getPostById(postId);
        if (AppUtils.isValidUserRequest(post.getUser())) {
            try {
                AppUtils.deleteFile(post.getThumbnail());
            } catch (IOException e) {
                e.printStackTrace();
            }
            postService.removePost(post);
            redirectAttributes.addFlashAttribute("postDeleteSuccessMessage", messageSource.getMessage("post.deleted", null, LocaleContextHolder.getLocale()));
        } else {
            redirectAttributes.addFlashAttribute("userAccessDenied", messageSource.getMessage("user.access.denied", null, LocaleContextHolder.getLocale()));
        }
        return REDIRECT_USER_HOME;
    }

    @GetMapping("/post")
    public String getCreatePostPage(Model model) {
        model.addAttribute("post", new PostData());
        model.addAttribute("categoryList", postService.getAllCategory());
        return CREATE_POST_PAGE;
    }

    @GetMapping("/post/single")
    public String getSinglePost(Model model) {
        return "blog/blog";
    }
}

