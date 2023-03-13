package cmc.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.Arrays;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {

        final String securitySchemeName = "Authorization";

        Info info = new Info().title("Prota").version(appVersion)
                .description("api 명세서");

        Components components = new Components()
                .addSecuritySchemes(
                        securitySchemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name(securitySchemeName)
                );

        return new OpenAPI()
                .components(components)
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }

}