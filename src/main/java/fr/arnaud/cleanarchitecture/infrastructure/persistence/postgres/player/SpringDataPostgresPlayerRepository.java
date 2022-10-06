package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.player;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPostgresPlayerRepository extends CrudRepository<PlayerEntity, UUID> {
}