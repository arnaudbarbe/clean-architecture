package fr.arnaud.cleanarchitecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CleanArchitectureApplication {
	@Profile(value = { "test", "docker", "loc" })
	public static void main(final String[] args) {
		SpringApplication.run(CleanArchitectureApplication.class, args);
	}

}
