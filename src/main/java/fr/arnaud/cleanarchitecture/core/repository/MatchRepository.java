package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entities.Match;

public interface MatchRepository {
	
    Match findById(UUID id);

    void save(Match match);
    
    List<Match> findAll();

    void delete(UUID id);

    void update(UUID id, Match match);
}