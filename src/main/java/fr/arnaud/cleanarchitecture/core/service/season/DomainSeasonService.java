package fr.arnaud.cleanarchitecture.core.service.season;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Season;
import fr.arnaud.cleanarchitecture.core.repository.SeasonRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainSeasonService implements SeasonService {

    private final SeasonRepository seasonRepository;

    public DomainSeasonService(@NotNull final SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public UUID createSeason(@NotNull final Season season) {
        this.seasonRepository.save(season);

        return season.getId();
    }

    @Override
    public void deleteSeason(@NotNull final UUID id) {
        this.seasonRepository.delete(id);
    }

    @Override
    public Season getSeason(@NotNull final UUID id) {
        return this.seasonRepository.findById(id);
    }

	@Override
	public List<Season> getSeasons() {
		return this.seasonRepository.findAll();
	}

	@Override
	public void updateSeason(@NotNull final UUID id, @NotNull final Season season) {
        this.seasonRepository.update(id, season);
	}
}
