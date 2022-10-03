package fr.arnaud.cleanarchitecture.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI api() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("accessToken",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").in(SecurityScheme.In.HEADER).name("Authorization")))

				.info(new Info().title("Test Server").description("This is the API description to the server").version("1.0")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
