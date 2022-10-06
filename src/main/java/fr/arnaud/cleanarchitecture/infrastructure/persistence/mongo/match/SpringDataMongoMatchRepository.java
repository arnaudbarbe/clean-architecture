package fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.match;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoMatchRepository extends MongoRepository<MatchEntity, UUID> {
}
