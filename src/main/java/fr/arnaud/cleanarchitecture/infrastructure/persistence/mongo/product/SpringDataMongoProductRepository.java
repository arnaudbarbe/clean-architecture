package fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.product;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import fr.arnaud.cleanarchitecture.core.model.Product;

@Repository
public interface SpringDataMongoProductRepository extends MongoRepository<Product, UUID> {
}
