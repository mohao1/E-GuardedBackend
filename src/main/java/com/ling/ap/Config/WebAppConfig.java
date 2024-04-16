package com.ling.ap.Config;


import com.ling.ap.Utils.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
//                                拦截所有请求
                .addPathPatterns("/**")
//                放行登录请求还有注册请求
                .excludePathPatterns(
                        "/index.html",
                        "/favicon.ico",
                        "/static/**",
                        "/Login/**",
                        "/Registration/**"
                );
    }
}
