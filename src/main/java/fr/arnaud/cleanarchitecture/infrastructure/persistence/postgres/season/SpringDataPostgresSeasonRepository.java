package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.season;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPostgresSeasonRepository extends CrudRepository<SeasonEntity, UUID> {
}