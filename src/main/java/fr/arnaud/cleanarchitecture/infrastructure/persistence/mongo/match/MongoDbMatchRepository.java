package fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.match;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.model.Team;
import fr.arnaud.cleanarchitecture.core.repository.MatchRepository;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;

@Component
public class MongoDbMatchRepository implements MatchRepository {

    private final SpringDataMongoMatchRepository matchRepository;

    private final TeamRepository teamRepository;
    
    @Autowired
    public MongoDbMatchRepository(final SpringDataMongoMatchRepository matchRepository,
    		final TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Match findById(final UUID id) {
        Optional<MatchEntity> optionalMatchEntity = this.matchRepository.findById(id);
        if (optionalMatchEntity.isPresent()) {
        	
        	MatchEntity matchEntity = optionalMatchEntity.get();
        	
        	return mapTomodel(matchEntity);
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
		.map(this::mapTomodel).toList();
	}

	@Override
	public void delete(final UUID id) {
        this.matchRepository.deleteById(id);
	}

	@Override
	public void update(final UUID id, final Match match) {
        Optional<MatchEntity> optionalMatchEntity = this.matchRepository.findById(id);
        if (optionalMatchEntity.isPresent()) {
        	MatchEntity matchEntity = optionalMatchEntity.get();
        	matchEntity.fromModel(match);
        	this.matchRepository.save(matchEntity);
        } else {
            throw new EntityNotFoundException("Match with id " + id + " not found");
        }
	}
	
    private Match mapTomodel(final MatchEntity matchEntity) {
    	Team homeTeam = this.teamRepository.findById(UUID.fromString(matchEntity.getHomeTeamId()));
    	Team outsideTeam = this.teamRepository.findById(UUID.fromString(matchEntity.getOutsideTeamId()));
    	
    	if(homeTeam == null) {
    		throw new EntityNotFoundException("Team with id " + matchEntity.getHomeTeamId());
    	}
    	if(outsideTeam == null) {
    		throw new EntityNotFoundException("Team with id " + matchEntity.getOutsideTeamId());
    	}
    	
        return matchEntity.toModel(homeTeam, outsideTeam);
	}
}
