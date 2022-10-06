package fr.arnaud.cleanarchitecture.core.service.match;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.repository.MatchRepository;

public class DomainMatchService implements MatchService {

    private final MatchRepository matchRepository;

    public DomainMatchService(@NotNull final MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public UUID createMatch(@NotNull final Match match) {
        this.matchRepository.save(match);

        return match.getId();
    }

    @Override
    public void deleteMatch(@NotNull final UUID id) {
        this.matchRepository.delete(id);
    }

    @Override
    public Match getMatch(@NotNull final UUID id) {
        return this.matchRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Match with given id doesn't exist"));
    }

	@Override
	public List<Match> getMatchs() {
		return this.matchRepository.findAll();
	}

    @Override
    public Match updateMatch(@NotNull final UUID id, @NotNull final Match match) {
        return this.matchRepository.update(id, match);
    }
}
