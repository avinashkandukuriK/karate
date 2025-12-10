package com.pharmacy.automation.test.support;

import org.springframework.context.ConfigurableApplicationContext;

public final class SpringContextHolder {

    private static ConfigurableApplicationContext context;

    private SpringContextHolder() {
    }

    public static void setContext(ConfigurableApplicationContext ctx) {
        context = ctx;
    }

    public static ConfigurableApplicationContext getContext() {
        if (context == null) {
            throw new IllegalStateException("Spring context has not been initialized for Karate tests");
        }
        return context;
    }
}
