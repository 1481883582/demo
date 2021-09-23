package com.openfeign_demo.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Profile({"dev","pro"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApiTask() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("openFeign")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.regex("/openFeign.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger测试")
//                .contact(new Contact("Bryan", "http://blog.bianxh.top/", ""))
                .version("1.0")
                .description("API")
                .build();
    }
}
