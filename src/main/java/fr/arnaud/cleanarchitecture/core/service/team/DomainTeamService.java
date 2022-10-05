package fr.arnaud.cleanarchitecture.core.service.team;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;

public class DomainTeamService implements TeamService {

    private final TeamRepository playerRepository;

    public DomainTeamService(final TeamRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UUID createTeam(final Team player) {
        this.playerRepository.save(player);

        return player.getId();
    }

    @Override
    public void deleteTeam(final UUID id) {
        this.playerRepository.delete(id);
    }

    @Override
    public Team getTeam(final UUID id) {
        return this.playerRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Team with given id doesn't exist"));
    }

	@Override
	public List<Team> getTeams() {
		return this.playerRepository.findAll();
	}
}
