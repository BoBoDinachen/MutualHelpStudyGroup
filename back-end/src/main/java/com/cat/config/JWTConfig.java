package com.cat.config;

import com.cat.interceptors.JwtTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * date: 2021/12/29 10:53
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Configuration
public class JWTConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList =new ArrayList<String>();
        excludeList.add("/user/login");
        excludeList.add("/user/register");
        excludeList.add("/download/**");
        registry.addInterceptor(new JwtTokenInterceptor()).
                addPathPatterns("/**").
                excludePathPatterns(excludeList);
    }
}
