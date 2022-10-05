package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.League;

public interface LeagueRepository {
	
    Optional<League> findById(UUID id);

    void save(League league);
    
    List<League> findAll();
    
    void delete(UUID id);

    League update(UUID id, League league);
}