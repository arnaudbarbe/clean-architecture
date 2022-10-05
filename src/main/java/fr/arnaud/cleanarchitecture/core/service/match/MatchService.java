package fr.arnaud.cleanarchitecture.core.service.match;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Match;

public interface MatchService {
	
    UUID createMatch(Match match);

    void deleteMatch(UUID id);
    
    Match getMatch(UUID id);
    
    List<Match> getMatchs();

    Match updateMatch(UUID id, Match match);
}