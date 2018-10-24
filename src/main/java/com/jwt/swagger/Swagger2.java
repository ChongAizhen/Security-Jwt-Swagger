package com.jwt.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class Swagger2 {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Security-Jwt-Swagger")
                .description("https://github.com/ChongAizhen")
                .termsOfServiceUrl("https://github.com/ChongAizhen")
                .contact("ChongAizhen")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket thingsboardApi() {
        TypeResolver typeResolver = new TypeResolver();
        final ResolvedType jsonNodeType =
                typeResolver.resolve(
                        JsonNode.class);
        final ResolvedType stringType =
                typeResolver.resolve(
                        String.class);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Security-Jwt-Swagger")
                .apiInfo(apiInfo())
                .alternateTypeRules(
                        new AlternateTypeRule(
                                jsonNodeType,
                                stringType))
                .select()
                .paths(apiPaths())
                .build()
                .securitySchemes(newArrayList(jwtTokenKey()))
                .securityContexts(newArrayList(securityContext()));
    }

    private ApiKey jwtTokenKey() {
        return new ApiKey("Authorization", "JWT token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(securityPaths())
                .build();
    }

    private Predicate<String> apiPaths() {
        return regex("/api.*");
    }

    private Predicate<String> securityPaths() {
        return and(
                regex("/api.*"),
                not(regex("/api/noauth.*"))
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("SYS_ADMIN", "System administrator");
        authorizationScopes[1] = new AuthorizationScope("TENANT_ADMIN", "Tenant administrator");
        authorizationScopes[2] = new AuthorizationScope("CUSTOMER_USER", "Customer");
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }

}
