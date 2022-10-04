package fr.arnaud.cleanarchitecture.infrastructure.configuration.mongo;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.order.SpringDataMongoOrderRepository;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoOrderRepository.class)
public class MongoDBConfiguration {
}
