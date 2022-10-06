package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.league;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPostgresLeagueRepository extends CrudRepository<LeagueEntity, UUID> {
}