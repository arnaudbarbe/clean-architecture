package fr.arnaud.cleanarchitecture.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPostgresLeagueRepository extends CrudRepository<LeagueEntity, UUID> {
}