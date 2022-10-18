package com.prom.eng.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableWebMvc
public class ApplicationConfig {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metadata());
    }

    private ApiInfo metadata() {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream("/version.properties")) {
            final Properties prop = new Properties();
            prop.load(resourceAsStream);
            return new ApiInfoBuilder()
                    .title("Promotion Engine Service")
                    .description("Promotion Engine RESTful APIs")
                    .version(prop.getProperty("project.version"))
                    .build();
        } catch (final IOException e) {
            log.error("Unable to load properties.", e);
        }
        return null;
    }
    
}