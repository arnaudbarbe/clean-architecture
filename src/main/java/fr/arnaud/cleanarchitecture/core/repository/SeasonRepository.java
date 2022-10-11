package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entity.Season;

public interface SeasonRepository {
	
    Season findById(UUID id);

    void save(Season season);
    
    List<Season> findAll();

    void delete(UUID id);

    void update(UUID id, Season season);
}