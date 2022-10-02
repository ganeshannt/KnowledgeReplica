package com.knowledgereplica;

import com.knowledgereplica.controller.AdminController;
import com.knowledgereplica.controller.AuthenticationController;
import com.knowledgereplica.controller.HomeController;
import com.knowledgereplica.controller.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class KnowledgeReplicaApplicationTests extends AbstractTestBase {

    @Autowired
    HomeController homeController;

    @Autowired
    UserController userController;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    AdminController adminController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(homeController);
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(authenticationController);
        Assertions.assertNotNull(adminController);
    }

}
