package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Championship;

public interface ChampionshipRepository {
	
    Championship findById(UUID id);

    void save(Championship championship);
    
    List<Championship> findAll();

    void delete(UUID id);

    void update(UUID id, Championship championship);
}