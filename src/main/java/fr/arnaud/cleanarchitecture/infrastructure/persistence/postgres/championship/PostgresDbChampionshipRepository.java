package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.championship;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.entities.Championship;
import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;

@Component
public class PostgresDbChampionshipRepository implements ChampionshipRepository {

    private final SpringDataPostgresChampionshipRepository championshipRepository;

    @Autowired
    public PostgresDbChampionshipRepository(final SpringDataPostgresChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }

    @Override
    public Championship findById(final UUID id) {
        Optional<ChampionshipEntity> championshipEntity = this.championshipRepository.findById(id);
        if (championshipEntity.isPresent()) {
            return championshipEntity.get().toModel();
        } else {
            return null;
        }
    }

    @Override
    public void save(final Championship championship) {
        this.championshipRepository.save(new ChampionshipEntity(championship));
    }

	@Override
	public List<Championship> findAll() {

		return StreamSupport.stream(this.championshipRepository.findAll().spliterator(), false)
		.map(ChampionshipEntity::toModel).toList();
	}

	@Override
	public void delete(final UUID id) {
        this.championshipRepository.deleteById(id);
	}

	@Override
	public void update(final UUID id, final Championship championship) {
        Optional<ChampionshipEntity> optionalChampionshipEntity = this.championshipRepository.findById(id);
        if (optionalChampionshipEntity.isPresent()) {
        	ChampionshipEntity championshipEntity = optionalChampionshipEntity.get();
        	championshipEntity.fromModel(championship);
        } else {
            throw new EntityNotFoundException("Championship with id " + id + " not found");
        }
	}
}
