package fr.arnaud.cleanarchitecture.infrastructure.configuration.cassandra;

import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.order.SpringDataCassandraOrderRepository;

@EnableCassandraRepositories(basePackageClasses = SpringDataCassandraOrderRepository.class)
public class CassandraConfiguration {

}