package fr.arnaud.cleanarchitecture.core.service.team;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public class DomainTeamService implements TeamService {

	private final TeamRepository teamRepository;

	public DomainTeamService(final TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Override
	public UUID createTeam(final Team team) {
		log.info("Create team {}", team);
		this.teamRepository.save(team);

		return team.getId();
	}

	@Override
	public void deleteTeam(final UUID id) {
		log.info("Delete team {}", id);
		this.teamRepository.delete(id);
	}

	@Override
	public Team getTeam(final UUID id) {
		log.info("Get team {}", id);
		return this.teamRepository.findById(id);
	}

	@Override
	public List<Team> getTeams() {
		log.info("Get teams");
		return this.teamRepository.findAll();
	}

	@Override
	public void updateTeam(final UUID id, final Team team) {
		log.info("Update team {} {}", id, team);
		this.teamRepository.update(id, team);
	}
}
