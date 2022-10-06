package fr.arnaud.cleanarchitecture.core.service.team;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;

public class DomainTeamService implements TeamService {

    private final TeamRepository playerRepository;

    public DomainTeamService(@NotNull final TeamRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UUID createTeam(@NotNull final Team player) {
        this.playerRepository.save(player);

        return player.getId();
    }

    @Override
    public void deleteTeam(@NotNull final UUID id) {
        this.playerRepository.delete(id);
    }

    @Override
    public Team getTeam(@NotNull final UUID id) {
        return this.playerRepository.findById(id);
    }

	@Override
	public List<Team> getTeams() {
		return this.playerRepository.findAll();
	}

	@Override
	public void updateTeam(@NotNull final UUID id, @NotNull final Team team) {
        this.playerRepository.update(id, team);
	}
}
