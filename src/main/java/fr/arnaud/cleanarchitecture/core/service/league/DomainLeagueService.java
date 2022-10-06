package fr.arnaud.cleanarchitecture.core.service.league;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.League;
import fr.arnaud.cleanarchitecture.core.repository.LeagueRepository;

public class DomainLeagueService implements LeagueService {

    private final LeagueRepository leagueRepository;

    public DomainLeagueService(@NotNull final LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public UUID createLeague(@NotNull final League league) {
        this.leagueRepository.save(league);

        return league.getId();
    }

    @Override
    public void deleteLeague(@NotNull final UUID id) {
        this.leagueRepository.delete(id);
    }

    @Override
    public League getLeague(@NotNull final UUID id) {
        return this.leagueRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException("League with given id doesn't exist"));
    }

	@Override
	public List<League> getLeagues() {
		return this.leagueRepository.findAll();
	}

    @Override
    public League updateLeague(@NotNull final UUID id, @NotNull final League league) {
        return this.leagueRepository.update(id, league);
    }
}
