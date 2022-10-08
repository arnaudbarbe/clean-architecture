package fr.arnaud.cleanarchitecture.core.service.match;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.entities.Match;

public interface MatchService {
	
    UUID createMatch(@NotNull Match match);

    void deleteMatch(@NotNull UUID id);
    
    Match getMatch(@NotNull UUID id);
    
    List<Match> getMatchs();

    void updateMatch(@NotNull UUID id, @NotNull Match match);
}