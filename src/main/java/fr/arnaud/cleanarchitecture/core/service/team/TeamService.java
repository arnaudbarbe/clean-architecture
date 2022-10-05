package fr.arnaud.cleanarchitecture.core.service.team;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Team;

public interface TeamService {
	
    UUID createTeam(Team player);

    void deleteTeam(UUID id);
    
    Team getTeam(UUID id);
    
    List<Team> getTeams();
}