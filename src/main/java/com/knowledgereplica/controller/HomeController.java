package com.knowledgereplica.controller;

import com.knowledgereplica.constant.ModelAtrConstant;
import com.knowledgereplica.model.CategoryEntity;
import com.knowledgereplica.model.CommentEntity;
import com.knowledgereplica.model.PostEntity;
import com.knowledgereplica.model.data.CommentData;
import com.knowledgereplica.model.data.ContactData;
import com.knowledgereplica.model.data.NewsLetterData;
import com.knowledgereplica.service.ContactService;
import com.knowledgereplica.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/")
public class HomeController {

  public static final String CONTACT = "contact";
  private static final String POST_PAGE = "blog/post";
  private static final String BLOG_PAGE = "blog/blog";
  private static final String CONTACT_PAGE = "/contact";
  private static final String REDIRECT_CONTACT = "redirect:/contact";
  private static final String INDEX_PAGE = "index";
  private static final String REDIRECT_INDEX = "redirect:/";
  @Autowired PostService postService;
  @Autowired ContactService contactService;
  @Autowired MessageSource messageSource;

  @GetMapping
  public String getIndexPage(Model model) {
    model.addAttribute(ModelAtrConstant.LATEST_POST_LIST, getLatestPost(3));
    model.addAttribute(ModelAtrConstant.FEATURED_POST_LIST, getFeaturedPost(3));
    model.addAttribute(ModelAtrConstant.NEWSLETTER, new NewsLetterData());
    return INDEX_PAGE;
  }

  @ModelAttribute("contextPath")
  public String contextPath(final HttpServletRequest request) {
    return request.getContextPath();
  }

  @GetMapping("/contact")
  public String getContactPage(Model model) {
    model.addAttribute(CONTACT, new ContactData());
    return CONTACT_PAGE;
  }

  @PostMapping("/contact")
  public String saveContactDetails(
      @Valid @ModelAttribute(CONTACT) ContactData contactData,
      BindingResult bindingResult,
      final Model model,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute(CONTACT, contactData);
    } else {
      contactService.saveContact(contactData);
      redirectAttributes.addFlashAttribute(
          "contactSuccess",
          messageSource.getMessage(
              "contact.message.submitted", null, LocaleContextHolder.getLocale()));
    }
    return REDIRECT_CONTACT;
  }

  @PostMapping("/newsletter")
  public String saveSubscriber(
      @Valid @ModelAttribute(ModelAtrConstant.NEWSLETTER) NewsLetterData newsLetter,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      model.addAttribute(ModelAtrConstant.NEWSLETTER, newsLetter);
    } else {
      contactService.saveNewsLetter(newsLetter);
      redirectAttributes.addFlashAttribute(
          "newsletterSuccess",
          messageSource.getMessage(
              "newsletter.subscription.added", null, LocaleContextHolder.getLocale()));
    }
    return REDIRECT_INDEX;
  }

  @GetMapping("/blog")
  public String getBlogPage() {
    return BLOG_PAGE;
  }

  @GetMapping("/post/{postId}")
  public String getPostPage(@PathVariable("postId") long postId, final Model model) {
    PostEntity post = postService.getPostByIdAndUpdateViews(postId);
    if (!Objects.isNull(post)) {
      List<CommentEntity> commentList = postService.getAllCommentsByPost(post);
      if (!model.containsAttribute(ModelAtrConstant.COMMENT)) {
        model.addAttribute(ModelAtrConstant.COMMENT, new CommentData());
      }

      model.addAttribute(ModelAtrConstant.COMMENT_LIST, commentList);
      model.addAttribute(ModelAtrConstant.CATEGORY_LIST, postService.getAllCategory());
      model.addAttribute(ModelAtrConstant.POST, post);
      model.addAttribute(ModelAtrConstant.PREVIOUS_POST, postService.getPreviousPost(postId, post));
      model.addAttribute(ModelAtrConstant.NEXT_POST, postService.getNextPost(postId, post));
      model.addAttribute(ModelAtrConstant.LATEST_POST_LIST, getLatestPost(3));
    }
    return POST_PAGE;
  }

  @PostMapping("/post/{postId}/comment")
  public String savePostComment(
      @Valid @ModelAttribute(ModelAtrConstant.COMMENT) CommentData commentData,
      BindingResult bindingResult,
      @PathVariable("postId") long postId,
      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute(ModelAtrConstant.COMMENT, commentData);
      return getPostPage(postId, model);
    }
    PostEntity post = postService.getPostById(postId);
    if (Objects.isNull(post)) {
      return POST_PAGE;
    }
    postService.saveComment(commentData, post);
    return "redirect:/post/" + postId;
  }

  @GetMapping("/post")
  public String getPostListByCategory(
      @RequestParam(value = "category", required = false) String category,
      @RequestParam(value = "page", required = false) Optional<Integer> page,
      @RequestParam(value = "size", required = false) Optional<Integer> size,
      final Model model) {

    List<PostEntity> postEntityList;

    if (StringUtils.isEmpty(category)) {
      postEntityList = postService.getAllPost();
    } else {
      postEntityList = postService.getAllPostByCategoryId(category);
    }
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(4);
    Page<PostEntity> pageList =
        postService.findPaginated(PageRequest.of(currentPage - 1, pageSize), postEntityList);
    model.addAttribute(ModelAtrConstant.POST_LIST, pageList);
    int totalPages = pageList.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers =
          IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }
    model.addAttribute(ModelAtrConstant.CATEGORY_LIST, postService.getAllCategory());
    model.addAttribute(ModelAtrConstant.LATEST_POST_LIST, getLatestPost(3));
    return BLOG_PAGE;
  }

  protected List<PostEntity> getLatestPost(int numberOfPost) {
    Pageable getLatestPost = PageRequest.of(0, numberOfPost, Sort.by("createdAt").descending());
    return postService.getAllPost(getLatestPost);
  }

  protected List<PostEntity> getFeaturedPost(int numberOfPost) {
    Pageable getLatestPost = PageRequest.of(0, numberOfPost, Sort.by("views").descending());
    return postService.getAllPost(getLatestPost);
  }

  protected Set<PostEntity> getPostByCategory(String category) {
    CategoryEntity categoryEntity = postService.getCategoryById(category);
    return categoryEntity.getPosts();
  }

  @ModelAttribute("session_firstname")
  public String session(final HttpServletRequest request) {
    return (String) request.getSession().getAttribute("firstName");
  }
}
