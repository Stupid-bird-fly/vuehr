package org.sang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService getThreadPool() {
        System.out.println("ExecutorService getThreadPool()...");
        return new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
    }
}
