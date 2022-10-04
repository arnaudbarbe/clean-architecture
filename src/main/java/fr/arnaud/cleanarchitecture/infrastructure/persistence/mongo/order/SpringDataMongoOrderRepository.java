package fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.order;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.arnaud.cleanarchitecture.core.model.Order;

@Repository
public interface SpringDataMongoOrderRepository extends MongoRepository<Order, UUID> {
}
