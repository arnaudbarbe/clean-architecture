package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.product;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCassandraProductRepository extends CassandraRepository<ProductEntity, UUID> {
}