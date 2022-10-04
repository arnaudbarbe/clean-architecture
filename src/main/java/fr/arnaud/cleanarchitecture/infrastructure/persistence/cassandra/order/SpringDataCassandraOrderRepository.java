package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.order;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCassandraOrderRepository extends CassandraRepository<OrderEntity, UUID> {
}