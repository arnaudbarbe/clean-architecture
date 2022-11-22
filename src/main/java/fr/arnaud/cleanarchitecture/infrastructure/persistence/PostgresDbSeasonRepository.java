package fr.arnaud.cleanarchitecture.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Season;
import fr.arnaud.cleanarchitecture.core.repository.SeasonRepository;

@Component
public class PostgresDbSeasonRepository implements SeasonRepository {

    private final SpringDataPostgresSeasonRepository seasonRepository;

    @Autowired
    public PostgresDbSeasonRepository(final SpringDataPostgresSeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Season findById(final UUID id) {
        Optional<SeasonEntity> optionalSeasonEntity = this.seasonRepository.findById(id);
        if (optionalSeasonEntity.isPresent()) {
            return optionalSeasonEntity.get()
                .toEntity();
        } else {
            return null;
        }
    }

    @Override
    public void save(final Season season) {
        this.seasonRepository.save(new SeasonEntity(season));
    }

	@Override
	public List<Season> findAll() {

		return StreamSupport.stream(this.seasonRepository.findAll().spliterator(), false)
		.map(SeasonEntity::toEntity).toList();
	}

	@Override
	public void delete(final UUID id) {
		try {
			this.seasonRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Season with id " + id);
		}
	}

	@Override
	public void update(final UUID id, final Season season) {
		
        Optional<SeasonEntity> optionalSeasonEntity = this.seasonRepository.findById(id);
        if (optionalSeasonEntity.isPresent()) {
        	SeasonEntity seasonEntity = optionalSeasonEntity.get();
        	seasonEntity.fromEntity(season);
        	this.seasonRepository.save(seasonEntity);
        } else {
            throw new EntityNotFoundException("Season with id " + id + " not found");
        }
	}
}
