package com.pharmacy.automation.test.runner;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.pharmacy.automation.AutomationApplication;
import com.pharmacy.automation.test.hooks.LoggingHook;
import com.pharmacy.automation.test.reporting.ExtentReportGenerator;
import com.pharmacy.automation.test.support.SpringContextHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    @Test
    void runUsers() {
        Results results = Runner.path("classpath:features/users/users.feature")
                .reportDir("target/karate-reports")
                .outputCucumberJson(true)
                .hook(new LoggingHook())
                .parallel(1);
        ExtentReportGenerator.generate(results.getReportDir(), results);
    }
}
