package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entity.League;

public interface LeagueRepository {
	
    League findById(UUID id);

    void save(League league);
    
    List<League> findAll();
    
    void delete(UUID id);

    void update(UUID id, League league);
}