package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Match;

public interface MatchRepository {
	
    Optional<Match> findById(UUID id);

    void save(Match match);
    
    List<Match> findAll();

    void delete(UUID id);
}