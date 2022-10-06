package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.team;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPostgresTeamRepository extends CrudRepository<TeamEntity, UUID> {
}