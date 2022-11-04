package fr.arnaud.cleanarchitecture.infrastructure.configuration.cors;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CORSConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**").allowCredentials(true).allowedMethods("*").allowedHeaders("*").allowedOrigins("*").allowedHeaders("*");
	}
}
