package fr.arnaud.cleanarchitecture.infrastructure.configuration.mongo;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.match.SpringDataMongoMatchRepository;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoMatchRepository.class)
@Component
public class MongoDBConfiguration {
}
