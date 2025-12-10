package com.pharmacy.automation.test.runner;

import com.intuit.karate.junit5.Karate;
import com.pharmacy.automation.AutomationApplication;
import com.pharmacy.automation.test.support.SpringContextHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest(classes = AutomationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersRunner {

    @LocalServerPort
    private int port;

    @Autowired
    private ConfigurableApplicationContext context;

    @BeforeAll
    void setUp() {
        SpringContextHolder.setContext(context);
        System.setProperty("karate.server.port", String.valueOf(port));
    }

    @Karate.Test
    Karate runUsers() {
        return Karate.run("classpath:features/users/users.feature");
    }
}
