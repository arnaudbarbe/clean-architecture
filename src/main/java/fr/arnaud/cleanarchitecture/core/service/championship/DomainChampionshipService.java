package fr.arnaud.cleanarchitecture.core.service.championship;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.model.Championship;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;

public class DomainChampionshipService implements ChampionshipService {

    private final ChampionshipRepository leagueRepository;

    public DomainChampionshipService(final ChampionshipRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public UUID createChampionship(final Championship league) {
        this.leagueRepository.save(league);

        return league.getId();
    }

    @Override
    public void deleteChampionship(final UUID id) {
        this.leagueRepository.delete(id);
    }

    @Override
    public Championship getChampionship(final UUID id) {
        return this.leagueRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException("Championship with given id doesn't exist"));
    }

	@Override
	public List<Championship> getChampionships() {
		return this.leagueRepository.findAll();
	}
}
