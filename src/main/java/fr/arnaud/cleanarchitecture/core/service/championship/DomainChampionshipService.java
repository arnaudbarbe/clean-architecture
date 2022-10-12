package fr.arnaud.cleanarchitecture.core.service.championship;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entity.Championship;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainChampionshipService implements ChampionshipService {

    private final ChampionshipRepository championshipRepository;

    public DomainChampionshipService(final ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }

    @Override
    public UUID createChampionship(final Championship championship) {
        
        this.championshipRepository.save(championship);

        return championship.getId();
    }

    @Override
    public void deleteChampionship(final UUID id) {
        this.championshipRepository.delete(id);
    }

    @Override
    public Championship getChampionship(final UUID id) {
        return this.championshipRepository.findById(id);
    }

	@Override
	public List<Championship> getChampionships() {
		return this.championshipRepository.findAll();
	}

    @Override
    public void updateChampionship(final UUID id, final Championship championship) {
        this.championshipRepository.update(id, championship);
    }
}
