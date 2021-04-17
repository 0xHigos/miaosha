package com.xujie.miaosha.config;

import com.xujie.miaosha.services.MiaoshaUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Resource
    private UserArgumentResolver resolver;

    public void addArgumentResolvers( List<HandlerMethodArgumentResolver> argumentResolvers ) {
        argumentResolvers.add(resolver);

    }
}
