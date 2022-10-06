package fr.arnaud.cleanarchitecture.core.service.team;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainTeamService implements TeamService {

	private final TeamRepository teamRepository;

	public DomainTeamService(@NotNull final TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Override
	public UUID createTeam(@NotNull final Team team) {
		log.debug("create team %h ", team);
		this.teamRepository.save(team);

		return team.getId();
	}

	@Override
	public void deleteTeam(@NotNull final UUID id) {
		this.teamRepository.delete(id);
	}

	@Override
	public Team getTeam(@NotNull final UUID id) {
		return this.teamRepository.findById(id);
	}

	@Override
	public List<Team> getTeams() {
		return this.teamRepository.findAll();
	}

	@Override
	public void updateTeam(@NotNull final UUID id, @NotNull final Team team) {
		this.teamRepository.update(id, team);
	}
}
