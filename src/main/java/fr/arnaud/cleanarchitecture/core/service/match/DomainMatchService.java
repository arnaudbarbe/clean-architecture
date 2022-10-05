package fr.arnaud.cleanarchitecture.core.service.match;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.repository.MatchRepository;

public class DomainMatchService implements MatchService {

    private final MatchRepository matchRepository;

    public DomainMatchService(final MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public UUID createMatch(final Match match) {
        this.matchRepository.save(match);

        return match.getId();
    }

    @Override
    public void deleteMatch(final UUID id) {
        this.matchRepository.delete(id);
    }

    @Override
    public Match getMatch(final UUID id) {
        return this.matchRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Match with given id doesn't exist"));
    }

	@Override
	public List<Match> getMatchs() {
		return this.matchRepository.findAll();
	}

    @Override
    public Match updateMatch(UUID id, Match match) {
        return this.matchRepository.update(id, match);
    }
}
