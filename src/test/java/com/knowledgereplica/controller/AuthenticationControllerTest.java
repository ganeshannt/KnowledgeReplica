package com.knowledgereplica.controller;

import com.knowledgereplica.AbstractTestBase;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerTest extends AbstractTestBase {

    @Test
    void getLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(redirectedUrl(baseUrl + "/authenticate/login"))
                .andExpect(status().isFound());
    }

    @Test
    void getSignUpPage() throws Exception {
        mockMvc.perform(get("/authenticate/signup"))
                .andExpect(status().isOk());
    }

    @Test
    void getForgotPasswordPage() throws Exception {
        mockMvc.perform(get("/authenticate/forgot/password"))
                .andExpect(status().isOk());
    }

    @Test
    void getAccessDeniedPage() throws Exception {
        mockMvc.perform(get("/access/denied"))
                .andExpect(redirectedUrl(baseUrl + "/authenticate/login"))
                .andExpect(status().isFound());
    }
}