package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Player;
import fr.arnaud.cleanarchitecture.core.repository.PlayerRepository;

@Component
public class PostgresDbPlayerRepository implements PlayerRepository {

    private final SpringDataPostgresPlayerRepository playerRepository;

    @Autowired
    public PostgresDbPlayerRepository(final SpringDataPostgresPlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(final UUID id) {
        Optional<PlayerEntity> optionalPlayerEntity = this.playerRepository.findById(id);
        if (optionalPlayerEntity.isPresent()) {
            return optionalPlayerEntity.get()
                .toModel();
        } else {
            return null;
        }
    }

    @Override
    public void save(final Player player) {
        this.playerRepository.save(new PlayerEntity(player));
    }

	@Override
	public List<Player> findAll() {

		return StreamSupport.stream(this.playerRepository.findAll().spliterator(), false)
		.map(PlayerEntity::toModel).toList();
	}

	@Override
	public void delete(final UUID id) {
        this.playerRepository.deleteById(id);
	}

	@Override
	public void update(final UUID id, final Player player) {
		
        Optional<PlayerEntity> optionalPlayerEntity = this.playerRepository.findById(id);
        if (optionalPlayerEntity.isPresent()) {
        	PlayerEntity playerEntity = optionalPlayerEntity.get();
        	playerEntity.fromModel(player);
        } else {
            throw new EntityNotFoundException("Player with id " + id + " not found");
        }
	}
}
