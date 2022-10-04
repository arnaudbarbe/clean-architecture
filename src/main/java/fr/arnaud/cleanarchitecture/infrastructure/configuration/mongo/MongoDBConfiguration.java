package fr.arnaud.cleanarchitecture.infrastructure.configuration.mongo;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.order.SpringDataMongoOrderRepository;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoOrderRepository.class)
@Component
public class MongoDBConfiguration {
}
