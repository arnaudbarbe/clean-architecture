package fr.arnaud.cleanarchitecture.infrastructure.configuration;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.SpringDataMongoOrderRepository;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoOrderRepository.class)
public class MongoDBConfiguration {
}
