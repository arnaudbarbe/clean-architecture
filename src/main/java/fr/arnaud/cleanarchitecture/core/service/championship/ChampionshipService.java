package fr.arnaud.cleanarchitecture.core.service.championship;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Championship;

public interface ChampionshipService {
	
    UUID createChampionship(Championship league);

    void deleteChampionship(UUID id);
    
    Championship getChampionship(UUID id);
    
    List<Championship> getChampionships();
}