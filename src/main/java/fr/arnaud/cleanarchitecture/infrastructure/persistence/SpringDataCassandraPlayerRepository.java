package fr.arnaud.cleanarchitecture.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCassandraPlayerRepository extends CassandraRepository<PlayerEntity, UUID> {

}