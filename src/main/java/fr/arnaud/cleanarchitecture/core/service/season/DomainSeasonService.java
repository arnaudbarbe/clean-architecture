package fr.arnaud.cleanarchitecture.core.service.season;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entity.Season;
import fr.arnaud.cleanarchitecture.core.repository.SeasonRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainSeasonService implements SeasonService {

    private final SeasonRepository seasonRepository;

    public DomainSeasonService(final SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public UUID createSeason(final Season season) {
        log.info("Create season {}", season);
        this.seasonRepository.save(season);

        return season.getId();
    }

    @Override
    public void deleteSeason(final UUID id) {
        log.info("Delete season {}", id);
        this.seasonRepository.delete(id);
    }

    @Override
    public Season getSeason(final UUID id) {
        log.info("Get season {}", id);
        return this.seasonRepository.findById(id);
    }

	@Override
	public List<Season> getSeasons() {
        log.info("Get seasons");
		return this.seasonRepository.findAll();
	}

	@Override
	public void updateSeason(final UUID id, final Season season) {
        log.info("Update season {} {}", id, season);
        this.seasonRepository.update(id, season);
	}
}
