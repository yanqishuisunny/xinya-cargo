package com.commom.config;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: swagger配置类
 * @author: Carlos
 * @Date: 2019/7/8 14:49
 */
@EnableSwagger2
@Configuration
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true", matchIfMissing = true)
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        System.out.println("swagger");
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
//                .enable(swaggerEnable)
                .select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("欣阳普货").description("欣阳普货相关文档")
                .termsOfServiceUrl("http://www.xinyacargo.com").version("1.0").build();
    }

}
