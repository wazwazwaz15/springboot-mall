package com.bowei.springbootmall.util;

import com.bowei.springbootmall.insterceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
     registry.addInterceptor(new LogInterceptor())
             .addPathPatterns("/**");
    }
}
