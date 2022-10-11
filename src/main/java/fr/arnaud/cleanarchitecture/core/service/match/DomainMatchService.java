package fr.arnaud.cleanarchitecture.core.service.match;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.entity.Match;
import fr.arnaud.cleanarchitecture.core.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        return this.matchRepository.findById(id);
    }

	@Override
	public List<Match> getMatchs() {
		return this.matchRepository.findAll();
	}

    @Override
    public void updateMatch(@NotNull final UUID id, @NotNull final Match match) {
        this.matchRepository.update(id, match);
    }
}
