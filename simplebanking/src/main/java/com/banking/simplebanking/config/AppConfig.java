package com.banking.simplebanking.config;

import com.banking.simplebanking.security.AdminAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<AdminAuthFilter> loggingFilter(AdminAuthFilter filter) {
        FilterRegistrationBean<AdminAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/bank/*"); // Filter applied to bank routes
        return registrationBean;
    }
}
