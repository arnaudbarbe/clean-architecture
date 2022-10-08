package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entities.Team;

public interface TeamRepository {
	
    Team findById(UUID id);

    void save(Team team);
    
    List<Team> findAll();

    void delete(UUID id);

    void update(UUID id, Team team);
}