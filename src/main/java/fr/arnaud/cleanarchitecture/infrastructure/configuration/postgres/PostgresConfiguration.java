package fr.arnaud.cleanarchitecture.infrastructure.configuration.postgres;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(
		basePackages = {"fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres"})
@EntityScan(
		basePackages = {"fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres"})

@EnableTransactionManagement
@Component
public class PostgresConfiguration {

}
