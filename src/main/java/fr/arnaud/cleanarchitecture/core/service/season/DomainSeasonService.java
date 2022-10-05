package fr.arnaud.cleanarchitecture.core.service.season;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Season;
import fr.arnaud.cleanarchitecture.core.repository.SeasonRepository;

public class DomainSeasonService implements SeasonService {

    private final SeasonRepository playerRepository;

    public DomainSeasonService(final SeasonRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UUID createSeason(final Season player) {
        this.playerRepository.save(player);

        return player.getId();
    }

    @Override
    public void deleteSeason(final UUID id) {
        this.playerRepository.delete(id);
    }

    @Override
    public Season getSeason(final UUID id) {
        return this.playerRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Season with given id doesn't exist"));
    }

	@Override
	public List<Season> getSeasons() {
		return this.playerRepository.findAll();
	}
}
