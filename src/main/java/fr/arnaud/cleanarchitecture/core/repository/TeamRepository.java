package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Team;

public interface TeamRepository {
	
    Optional<Team> findById(UUID id);

    void save(Team team);
    
    List<Team> findAll();

    void delete(UUID id);

    Team update(UUID id, Team team);
}