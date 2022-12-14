package fr.arnaud.cleanarchitecture.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Championship;
import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;
import fr.arnaud.cleanarchitecture.core.repository.MatchRepository;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;

@Component
public class MongoDbMatchRepository implements MatchRepository {

    private final SpringDataMongoMatchRepository matchRepository;

    private final TeamRepository teamRepository;
    
    private final ChampionshipRepository championshipRepository;
    
    @Autowired
    public MongoDbMatchRepository(final SpringDataMongoMatchRepository matchRepository,
    		final TeamRepository teamRepository, final ChampionshipRepository championshipRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.championshipRepository = championshipRepository;
    }

    @Override
    public Match findById(final UUID id) {
        Optional<MatchEntity> optionalMatchEntity = this.matchRepository.findById(id);
        if (optionalMatchEntity.isPresent()) {
        	
        	MatchEntity matchEntity = optionalMatchEntity.get();
        	
        	return mapToEntity(matchEntity);
        } else {
            return null;
        }
    }

	@Override
    public void save(final Match match) {
        this.matchRepository.save(new MatchEntity(match));
    }
    
	@Override
	public List<Match> findAll() {
		return StreamSupport.stream(this.matchRepository.findAll().spliterator(), false)
		.map(this::mapToEntity).toList();
	}

	@Override
	public void delete(final UUID id) {
        Optional<MatchEntity> optionalMatchEntity = this.matchRepository.findById(id);
        if (optionalMatchEntity.isPresent()) {
			this.matchRepository.deleteById(id);
        } else {
        	throw new EntityNotFoundException("Match with id " + id);
        }

	}

	@Override
	public void update(final UUID id, final Match match) {
        Optional<MatchEntity> optionalMatchEntity = this.matchRepository.findById(id);
        if (optionalMatchEntity.isPresent()) {
        	MatchEntity matchEntity = optionalMatchEntity.get();
        	matchEntity.fromEntity(match);
        	this.matchRepository.save(matchEntity);
        } else {
            throw new EntityNotFoundException("Match with id " + id + " not found");
        }
	}
	
    private Match mapToEntity(final MatchEntity matchEntity) {
    	Team homeTeam = this.teamRepository.findById(UUID.fromString(matchEntity.getHomeTeamId()));
    	Team outsideTeam = this.teamRepository.findById(UUID.fromString(matchEntity.getOutsideTeamId()));
    	
    	if(homeTeam == null) {
    		//FIXME : we return an empty team object when we can't find it into db
    		homeTeam = Team.builder().id(UUID.fromString(matchEntity.getHomeTeamId())).build();
    	}
    	if(outsideTeam == null) {
    		outsideTeam = Team.builder().id(UUID.fromString(matchEntity.getOutsideTeamId())).build();
    	}
    	
    	Championship championship = this.championshipRepository.findById(matchEntity.getChampionshipId());
    	
    	if(championship == null) {
    		//FIXME : we return an empty championship object when we can't find it into db
    		championship = Championship.builder().id(matchEntity.getChampionshipId()).build();
    	}

        return matchEntity.toEntity(homeTeam, outsideTeam, championship);
	}
}
