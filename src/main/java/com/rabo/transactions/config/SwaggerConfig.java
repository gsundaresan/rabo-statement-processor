package com.rabo.transactions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rabo.transactions.controller"))
                .build()
                .pathMapping("/");
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Rabo Statement Records Validator")
                .description("The service demonstrates working of Statement Records validation.")
                .contact("Guhan Sundaresan")
                .build();
    }
}
