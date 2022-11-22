package fr.arnaud.cleanarchitecture.infrastructure.configuration.mongo;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@EnableMongoRepositories(basePackages = {"fr.arnaud.cleanarchitecture.infrastructure.persistence"})
@Component
public class MongoDBConfiguration {
}
