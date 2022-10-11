package fr.arnaud.cleanarchitecture.core.service.team;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.entity.Team;

public interface TeamService {
	
    UUID createTeam(@NotNull Team player);

    void deleteTeam(@NotNull UUID id);
    
    Team getTeam(@NotNull UUID id);
    
    List<Team> getTeams();

    void updateTeam(UUID id, @NotNull Team team);
}