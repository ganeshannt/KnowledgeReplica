package com.knowledgereplica.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.knowledgereplica.AbstractTestBase;
import org.junit.jupiter.api.Test;

class HomeControllerTest extends AbstractTestBase {

  @Test
  void getIndexPage() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk());
  }

  @Test
  void getContactPage() throws Exception {
    this.mockMvc.perform(get("/contact")).andExpect(status().isOk());
  }

  @Test
  void getPostListByCategory() throws Exception {
    this.mockMvc.perform(get("/post")).andExpect(status().isOk());
  }
}
