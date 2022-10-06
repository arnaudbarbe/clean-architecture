package fr.arnaud.cleanarchitecture.core.service.league;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.League;

public interface LeagueService {
	
    UUID createLeague(@NotNull League league);

    void deleteLeague(@NotNull UUID id);
    
    League getLeague(@NotNull UUID id);
    
    List<League> getLeagues();

    void updateLeague(@NotNull UUID id, @NotNull League league);
}