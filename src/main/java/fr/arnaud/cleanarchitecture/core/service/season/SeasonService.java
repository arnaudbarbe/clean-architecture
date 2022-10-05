package fr.arnaud.cleanarchitecture.core.service.season;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Season;

public interface SeasonService {
	
    UUID createSeason(Season player);

    void deleteSeason(UUID id);
    
    Season getSeason(UUID id);
    
    List<Season> getSeasons();
}