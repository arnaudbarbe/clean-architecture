package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.championship;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.entity.Championship;
import fr.arnaud.cleanarchitecture.core.entity.Player;
import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;
import fr.arnaud.cleanarchitecture.core.repository.PlayerRepository;

@Component
public class PostgresDbChampionshipRepository implements ChampionshipRepository {

    private final SpringDataPostgresChampionshipRepository championshipRepository;
    
    private final PlayerRepository playerRepository;

    @Autowired
    public PostgresDbChampionshipRepository(final SpringDataPostgresChampionshipRepository championshipRepository, final PlayerRepository playerRepository) {
        this.championshipRepository = championshipRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Championship findById(final UUID id) {
        Optional<ChampionshipEntity> optionalChampionshipEntity = this.championshipRepository.findById(id);
        if (optionalChampionshipEntity.isPresent()) {
        	
        	ChampionshipEntity championshipEntity = optionalChampionshipEntity.get();
        	
        	return mapToEntity(championshipEntity);
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
		.map(this::mapToEntity).toList();
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
        	championshipEntity.fromEntity(championship);
        } else {
            throw new EntityNotFoundException("Championship with id " + id + " not found");
        }
	}
	
    private Championship mapToEntity(final ChampionshipEntity championshipEntity) {
    	Player player = this.playerRepository.findById(championshipEntity.getPlayerId());
    	
    	if(player == null) {
    		//FIXME : we return an empty team object when we can't find it into db
    		player = Player.builder().id(championshipEntity.getPlayerId()).build();
    	}
        return championshipEntity.toEntity(player);
	}
}
