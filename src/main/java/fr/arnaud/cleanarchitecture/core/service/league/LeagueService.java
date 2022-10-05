package fr.arnaud.cleanarchitecture.core.service.league;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.League;

public interface LeagueService {
	
    UUID createLeague(League league);

    void deleteLeague(UUID id);
    
    League getLeague(UUID id);
    
    List<League> getLeagues();

    League updateLeague(UUID id, League league);
}