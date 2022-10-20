package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.entity.Championship;
import fr.arnaud.cleanarchitecture.core.entity.Team;
import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;

@Component
public class PostgresDbTeamRepository implements TeamRepository {

    private final SpringDataPostgresTeamRepository teamRepository;

    private final ChampionshipRepository championshipRepository;

    @Autowired
    public PostgresDbTeamRepository(final SpringDataPostgresTeamRepository teamRepository
    		, final ChampionshipRepository championshipRepository) {
        this.teamRepository = teamRepository;
        this.championshipRepository = championshipRepository;
    }

    @Override
    public Team findById(final UUID id) {
        Optional<TeamEntity> optionalTeamEntity = this.teamRepository.findById(id);
        if (optionalTeamEntity.isPresent()) {
        	TeamEntity teamEntity = optionalTeamEntity.get();
        	
        	return mapToEntity(teamEntity);
        } else {
            return null;
        }
    }

    @Override
    public void save(final Team team) {
        this.teamRepository.save(new TeamEntity(team));
    }

	@Override
	public List<Team> findAll() {

		return StreamSupport.stream(this.teamRepository.findAll().spliterator(), false)
		.map(this::mapToEntity).toList();
	}

	@Override
	public void delete(final UUID id) {
		try {
			this.teamRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {}
	}

	@Override
	public void update(final UUID id, final Team team) {
		
        Optional<TeamEntity> optionalTeamEntity = this.teamRepository.findById(id);
        if (optionalTeamEntity.isPresent()) {
        	TeamEntity teamEntity = optionalTeamEntity.get();
        	teamEntity.fromEntity(team);
        	this.teamRepository.save(teamEntity);
        } else {
            throw new EntityNotFoundException("Team with id " + id + " not found");
        }
	}
	
    private Team mapToEntity(final TeamEntity teamEntity) {
    	Championship championship = this.championshipRepository.findById(teamEntity.getChampionship().getId());
    	
    	if(championship == null) {
    		//FIXME : we return an empty championship object when we can't find it into db
    		championship = Championship.builder().id(teamEntity.getChampionship().getId()).build();
    	}
        return teamEntity.toEntity(championship);
	}
}
