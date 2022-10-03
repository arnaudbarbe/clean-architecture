package fr.arnaud.cleanarchitecture.infrastructure.configuration;

import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.SpringDataCassandraOrderRepository;

@EnableCassandraRepositories(basePackageClasses = SpringDataCassandraOrderRepository.class)
public class CassandraConfiguration {

}