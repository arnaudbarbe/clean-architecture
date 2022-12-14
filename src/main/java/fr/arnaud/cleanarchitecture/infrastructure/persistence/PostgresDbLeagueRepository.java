package fr.arnaud.cleanarchitecture.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.League;
import fr.arnaud.cleanarchitecture.core.repository.LeagueRepository;

@Component
public class PostgresDbLeagueRepository implements LeagueRepository {

    private final SpringDataPostgresLeagueRepository leagueRepository;

    @Autowired
    public PostgresDbLeagueRepository(final SpringDataPostgresLeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public League findById(final UUID id) {
        Optional<LeagueEntity> optionalLeagueEntity = this.leagueRepository.findById(id);
        if (optionalLeagueEntity.isPresent()) {
            return optionalLeagueEntity.get()
                .toEntity();
        } else {
            return null;
        }
    }

    @Override
    public void save(final League league) {
        this.leagueRepository.save(new LeagueEntity(league));
    }

	@Override
	public List<League> findAll() {

		return StreamSupport.stream(this.leagueRepository.findAll().spliterator(), false)
		.map(LeagueEntity::toEntity).toList();
	}

	@Override
	public void delete(final UUID id) {
		try {
	        this.leagueRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("League with id " + id);
		}
	}

	@Override
	public void update(final UUID id, final League league) {
		
        Optional<LeagueEntity> optionalLeagueEntity = this.leagueRepository.findById(id);
        if (optionalLeagueEntity.isPresent()) {
        	LeagueEntity leagueEntity = optionalLeagueEntity.get();
        	leagueEntity.fromEntity(league);
        	this.leagueRepository.save(leagueEntity);
        } else {
            throw new EntityNotFoundException("League with id " + id + " not found");
        }
	}
}
