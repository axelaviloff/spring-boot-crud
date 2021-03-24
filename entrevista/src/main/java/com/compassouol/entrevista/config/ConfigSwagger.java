package com.compassouol.entrevista.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class ConfigSwagger {
	
	@Bean
    public Docket forumApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.compassouol.entrevista"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}