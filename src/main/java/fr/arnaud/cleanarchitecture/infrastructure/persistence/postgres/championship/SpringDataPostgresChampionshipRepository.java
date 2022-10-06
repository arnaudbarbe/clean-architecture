package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.championship;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPostgresChampionshipRepository extends CrudRepository<ChampionshipEntity, UUID> {
}