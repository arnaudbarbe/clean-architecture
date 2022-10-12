package fr.arnaud.cleanarchitecture.core.service.team;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entity.Team;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainTeamService implements TeamService {

	private final TeamRepository teamRepository;

	public DomainTeamService(final TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Override
	public UUID createTeam(final Team team) {
		log.debug("create team {} ", team);
		this.teamRepository.save(team);

		return team.getId();
	}

	@Override
	public void deleteTeam(final UUID id) {
		log.debug("delete team Id {} ", id);
		this.teamRepository.delete(id);
	}

	@Override
	public Team getTeam(final UUID id) {
		log.debug("get team Id {} ", id);
		return this.teamRepository.findById(id);
	}

	@Override
	public List<Team> getTeams() {
		log.debug("get teams");
		return this.teamRepository.findAll();
	}

	@Override
	public void updateTeam(final UUID id, final Team team) {
		log.debug("update team Id {} {} ", id, team);
		this.teamRepository.update(id, team);
	}
}
