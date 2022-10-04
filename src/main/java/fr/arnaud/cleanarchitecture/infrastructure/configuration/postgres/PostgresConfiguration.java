package fr.arnaud.cleanarchitecture.infrastructure.configuration.postgres;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.product.ProductEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.product.SpringDataPostgresProductRepository;

@EnableJpaRepositories(basePackageClasses = SpringDataPostgresProductRepository.class)
@EntityScan(basePackageClasses = ProductEntity.class)
@EnableTransactionManagement
@Component
public class PostgresConfiguration {

}
