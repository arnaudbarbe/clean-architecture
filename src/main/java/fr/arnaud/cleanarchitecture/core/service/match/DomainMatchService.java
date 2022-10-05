package fr.arnaud.cleanarchitecture.core.service.match;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.repository.MatchRepository;

public class DomainMatchService implements MatchService {

    private final MatchRepository leagueRepository;

    public DomainMatchService(final MatchRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public UUID createMatch(final Match league) {
        this.leagueRepository.save(league);

        return league.getId();
    }

    @Override
    public void deleteMatch(final UUID id) {
        this.leagueRepository.delete(id);
    }

    @Override
    public Match getMatch(final UUID id) {
        return this.leagueRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Match with given id doesn't exist"));
    }

	@Override
	public List<Match> getMatchs() {
		return this.leagueRepository.findAll();
	}
}
