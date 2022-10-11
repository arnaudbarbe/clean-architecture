package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.entities.Team;
import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.repository.TeamRepository;

@Component
public class PostgresDbTeamRepository implements TeamRepository {

    private final SpringDataPostgresTeamRepository teamRepository;

    @Autowired
    public PostgresDbTeamRepository(final SpringDataPostgresTeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team findById(final UUID id) {
        Optional<TeamEntity> optionalTeamEntity = this.teamRepository.findById(id);
        if (optionalTeamEntity.isPresent()) {
            return optionalTeamEntity.get().toEntity();
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
		.map(TeamEntity::toEntity).toList();
	}

	@Override
	public void delete(final UUID id) {
        this.teamRepository.deleteById(id);
	}

	@Override
	public void update(final UUID id, final Team team) {
		
        Optional<TeamEntity> optionalTeamEntity = this.teamRepository.findById(id);
        if (optionalTeamEntity.isPresent()) {
        	TeamEntity teamEntity = optionalTeamEntity.get();
        	teamEntity.fromEntity(team);
        } else {
            throw new EntityNotFoundException("Team with id " + id + " not found");
        }
	}
}
