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
		log.debug("create team {} ", team);
		this.teamRepository.save(team);

		return team.getId();
	}

	@Override
	public void deleteTeam(@NotNull final UUID id) {
		log.debug("delete team Id {} ", id);
		this.teamRepository.delete(id);
	}

	@Override
	public Team getTeam(@NotNull final UUID id) {
		log.debug("get team Id {} ", id);
		return this.teamRepository.findById(id);
	}

	@Override
	public List<Team> getTeams() {
		log.debug("get teams");
		return this.teamRepository.findAll();
	}

	@Override
	public void updateTeam(@NotNull final UUID id, @NotNull final Team team) {
		log.debug("update team Id {} {} ", id, team);
		this.teamRepository.update(id, team);
	}
}
