package com.knowledgereplica.controller;

import com.knowledgereplica.constant.ErrorConstant;
import com.knowledgereplica.exception.InvalidTokenException;
import com.knowledgereplica.exception.UserAlreadyExistException;
import com.knowledgereplica.model.data.ResetPasswordData;
import com.knowledgereplica.model.data.UserData;
import com.knowledgereplica.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@RequestMapping("/authenticate")
public class AuthenticationController {


    public static final String CHANGE_PASSWORD_PAGE = "authentication/change_password";
    public static final String FORGOT_PASSWORD_PAGE = "authentication/forgot_password";
    private static final String REDIRECT_LOGIN = "redirect:/authenticate/login";
    private static final String REDIRECT_SIGNUP = "redirect:/authenticate/signup";
    private static final String LOGIN_PAGE = "/authentication/login";
    private static final String SIGNUP_PAGE = "/authentication/signup";
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("user") UserData userData, BindingResult bindingResult, final Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userData);
            return SIGNUP_PAGE;
        }
        if (!userData.isPasswordMatches()) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Password mismatch");
            model.addAttribute("user", userData);
            return SIGNUP_PAGE;
        }
        try {
            authenticationService.register(userData);
        } catch (UserAlreadyExistException | MessagingException e) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
            model.addAttribute("user", userData);
            return SIGNUP_PAGE;
        }
        redirectAttributes.addFlashAttribute("registrationMessage", messageSource.getMessage("user.registration.verification.email.msg", null, LocaleContextHolder.getLocale()));
        return REDIRECT_SIGNUP;
    }

    @GetMapping("/signup/verify")
    public String verifyUser(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        try {
            boolean isVerified = authenticationService.userVerification(token);
            if (isVerified) {
                redirectAttributes.addFlashAttribute("registrationSuccessMessage", messageSource.getMessage("user.registration.verification.success", null, LocaleContextHolder.getLocale()));
            } else {
                redirectAttributes.addFlashAttribute("expiredTokenError", messageSource.getMessage("user.registration.verification.expired.token", null, LocaleContextHolder.getLocale()));
            }
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(ErrorConstant.INVALID_TOKEN_ERROR_ATR, messageSource.getMessage(ErrorConstant.VERIFICATION_INVALID_TOKEN_MSG_CODE, null, LocaleContextHolder.getLocale()));

        } catch (MessagingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(ErrorConstant.EMAIL_SERVICE_ERROR_ATR, messageSource.getMessage(ErrorConstant.EMAIL_SERVICE_ERROR_MSG_CODE, null, LocaleContextHolder.getLocale()));
        }
        return REDIRECT_LOGIN;
    }

    @PostMapping("/forgot/password")
    public String forgotPassword(@Valid @ModelAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR) ResetPasswordData resetPasswordData, final Model model, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(resetPasswordData.getEmail())) {
            model.addAttribute("emptyEmailError", messageSource.getMessage("user.reset.password.empty.email", null, LocaleContextHolder.getLocale()));
            model.addAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR, resetPasswordData);
            return FORGOT_PASSWORD_PAGE;
        }
        try {
            authenticationService.forgotPassword(resetPasswordData);
        } catch (MessagingException e) {
            e.printStackTrace();
            model.addAttribute(ErrorConstant.EMAIL_SERVICE_ERROR_ATR, messageSource.getMessage(ErrorConstant.EMAIL_SERVICE_ERROR_MSG_CODE, null, LocaleContextHolder.getLocale()));
            model.addAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR, resetPasswordData);
            return FORGOT_PASSWORD_PAGE;
        }
        redirectAttributes.addFlashAttribute("resetPasswordMessage", messageSource.getMessage("user.forgot.password.message", null, LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }

    @GetMapping("/change/password")
    public String changePasswordTokenVerify(@RequestParam(value = "token") String token, RedirectAttributes redirectAttributes, final Model model) {
        boolean isVerified;
        try {
            isVerified = authenticationService.verifyResetPasswordToken(token);
            if (!isVerified) {
                redirectAttributes.addFlashAttribute("expiredResetPasswordTokenError", messageSource.getMessage("user.reset.password.expired.token", null, LocaleContextHolder.getLocale()));
                return REDIRECT_LOGIN;
            }
        } catch (InvalidTokenException e) {
            redirectAttributes.addFlashAttribute(ErrorConstant.INVALID_TOKEN_ERROR_ATR, messageSource.getMessage(ErrorConstant.VERIFICATION_INVALID_TOKEN_MSG_CODE, null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        } catch (MessagingException e) {
            redirectAttributes.addFlashAttribute(ErrorConstant.EMAIL_SERVICE_ERROR_ATR, messageSource.getMessage(ErrorConstant.EMAIL_SERVICE_ERROR_MSG_CODE, null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        ResetPasswordData passwordData = new ResetPasswordData();
        passwordData.setToken(token);
        setChangePasswordPageData(model, passwordData);
        return CHANGE_PASSWORD_PAGE;
    }

    @PostMapping("/change/password")
    public String changePassword(@Valid @ModelAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR) ResetPasswordData resetPasswordData, RedirectAttributes redirectAttributes, final Model model) {
        if (StringUtils.isEmpty(resetPasswordData.getPassword()) || StringUtils.isEmpty(resetPasswordData.getConfirmPassword())) {
            model.addAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR, resetPasswordData);
            model.addAttribute("emptyFieldError", messageSource.getMessage("user.reset.password.empty.field", null, LocaleContextHolder.getLocale()));
            return CHANGE_PASSWORD_PAGE;
        }
        if (!StringUtils.equals(resetPasswordData.getPassword(), resetPasswordData.getConfirmPassword())) {
            model.addAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR, resetPasswordData);
            model.addAttribute("passwordMismatchError", messageSource.getMessage("user.reset.password.mismatch", null, LocaleContextHolder.getLocale()));
            return CHANGE_PASSWORD_PAGE;
        }
        try {
            authenticationService.resetPassword(resetPasswordData);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(ErrorConstant.INVALID_TOKEN_ERROR_ATR, messageSource.getMessage(ErrorConstant.VERIFICATION_INVALID_TOKEN_MSG_CODE, null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        redirectAttributes.addFlashAttribute("resetPasswordSuccess", messageSource.getMessage("user.password.update.message", null, LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }

    private void setChangePasswordPageData(Model model, ResetPasswordData resetPasswordData) {
        model.addAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR, resetPasswordData);
    }


//    Get static page detail

    @GetMapping("/404")
    public String get404Page() {
        return "authentication/404";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", defaultValue = "false") boolean loginError, @RequestParam(value = "invalid-session", defaultValue = "false") boolean invalidSession, RedirectAttributes redirectAttributes, final Model model) {
        if (loginError) {
            model.addAttribute("loginError", messageSource.getMessage("user.login.error", null, LocaleContextHolder.getLocale()));
        }
        if (invalidSession) {
            model.addAttribute("invalidSession", messageSource.getMessage("user.invalid.session", null, LocaleContextHolder.getLocale()));
        }
        return LOGIN_PAGE;
    }

    @GetMapping("/signup")
    public String getSignUpPage(final Model model) {
        model.addAttribute("user", new UserData());
        return SIGNUP_PAGE;
    }

    @GetMapping("/forgot/password")
    public String getForgotPasswordPage(final Model model) {
        model.addAttribute(ErrorConstant.RESET_PASSWORD_DATA_ATR, new ResetPasswordData());
        return FORGOT_PASSWORD_PAGE;
    }

    @GetMapping("/access/denied")
    public String getAccessDeniedPage() {
        return "authentication/access_denied";
    }

}
