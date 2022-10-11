package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.entity.Player;
import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.repository.PlayerRepository;

@Component
public class CassandraDbPlayerRepository implements PlayerRepository {

    private final SpringDataCassandraPlayerRepository playerRepository;

    @Autowired
    public CassandraDbPlayerRepository(final SpringDataCassandraPlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(final UUID id) {
        Optional<PlayerEntity> playerEntity = this.playerRepository.findById(id);
        if (playerEntity.isPresent()) {
            return playerEntity.get()
                .toEntity();
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

		return this.playerRepository.findAll()
		.stream()
		.map(PlayerEntity::toEntity).toList();
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
        	playerEntity.fromEntity(player);
        } else {
            throw new EntityNotFoundException("Player with id " + id + " not found");
        }
	}
}
