package com.basic.javaframe.common.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.basic.javaframe.controller.BaseController;

/**
 * 配置拦截器
* <p>Title: InterceptorConfig</p>  
* <p>Description: </p>  
* @author hero
 */
@Configuration
public class InterceptorConfig extends BaseController implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/wx/**","/file/**","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");   
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	 registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    	
	  //文件磁盘url 映射
	  registry.addResourceHandler("/file/**").addResourceLocations("file:/"+filePath);
    }
    
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
    
    @Bean 
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //允许上传的文件最大值
        factory.setMaxFileSize("50MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("50MB");  
        return factory.createMultipartConfig();  
    }
}
