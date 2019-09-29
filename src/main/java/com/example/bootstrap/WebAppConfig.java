package com.example.bootstrap;


import com.example.bootstrap.intercepter.LoginIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebAppConfig implements WebMvcConfigurer {
/*    private final static List<String> resources;
    static {
        resources = new ArrayList<>();
        resources.add("/js/**");
        resources.add("/css/**");
        resources.add("/img/**");
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/login/**","/register/**","/share/**").excludePathPatterns("/js/**","/css/**","/img/**","/fonts/**");
    }

    @Bean
    public LoginIntercepter getLoginInterceptor() {
        return new LoginIntercepter();
    }

}
