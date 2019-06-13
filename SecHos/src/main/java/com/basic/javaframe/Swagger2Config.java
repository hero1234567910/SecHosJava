package com.basic.javaframe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.basic.javaframe.common.customclass.PassToken;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    //swagger2的基本配置
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                //接口包扫描
                .apis(RequestHandlerSelectors.basePackage("com.basic.javaframe"))
                .paths(PathSelectors.any()).build();
    }
 
    //构建api文档信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("使用swagger2构建后台的接口文档")
                //创建人相关信息
                .contact(new Contact("litongxue09", "www.inyourheart.con", "xxx@qq.com"))
                //描述
                .description("接口文档，这是描述")
                //版本 
                .version("1.0")
                .build();
    }
 
}

