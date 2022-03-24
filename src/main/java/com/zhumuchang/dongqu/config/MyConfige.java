package com.zhumuchang.dongqu.config;

import com.zhumuchang.dongqu.config.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author sx
 * @Description 自定义配置类
 * @Date 2022/3/6 14:18
 */
@Configuration
public class MyConfige implements WebMvcConfigurer{

//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void configurePathMatch(PathMatchConfigurer configurer) {
//                UrlPathHelper urlPathHelper = new UrlPathHelper();
//                urlPathHelper.setRemoveSemicolonContent(false);
//                configurer.setUrlPathHelper(urlPathHelper);
//            }
//        };
//    }

    @Value("${jwt.tokenSecret}")
    private String tokenSecret;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(jwtInterceptor());
        //拦截请求
//        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.addPathPatterns("/user/**");
    }

    /**
     * jwt拦截器
     *
     * @return
     */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(tokenSecret);
    }
}
