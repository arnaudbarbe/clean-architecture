package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Season;

public interface SeasonRepository {
	
    Optional<Season> findById(UUID id);

    void save(Season season);
    
    List<Season> findAll();

    void delete(UUID id);

    Season update(UUID id, Season season);
}