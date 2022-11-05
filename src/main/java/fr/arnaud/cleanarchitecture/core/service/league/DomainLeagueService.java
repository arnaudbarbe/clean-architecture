package fr.arnaud.cleanarchitecture.core.service.league;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.League;
import fr.arnaud.cleanarchitecture.core.repository.LeagueRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public class DomainLeagueService implements LeagueService {

    private final LeagueRepository leagueRepository;

    public DomainLeagueService(final LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public UUID createLeague(final League league) {
        log.info("Create league {}", league);
        this.leagueRepository.save(league);

        return league.getId();
    }

    @Override
    public void deleteLeague(final UUID id) {
        log.info("Delete league {}", id);
        try {
			this.leagueRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("League with id " + id);
		}
    }

    @Override
    public League getLeague(final UUID id) {
        log.info("Get league {}", id);
        return this.leagueRepository.findById(id);
    }

	@Override
	public List<League> getLeagues() {
        log.info("Get leagues");
		return this.leagueRepository.findAll();
	}

    @Override
    public void updateLeague(final UUID id, final League league) {
        log.info("Update league {} {}", id, league);
        this.leagueRepository.update(id, league);
    }
}
