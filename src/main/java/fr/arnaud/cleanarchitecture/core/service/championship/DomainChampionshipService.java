package fr.arnaud.cleanarchitecture.core.service.championship;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Championship;
import fr.arnaud.cleanarchitecture.core.repository.ChampionshipRepository;

public class DomainChampionshipService implements ChampionshipService {

    private final ChampionshipRepository championshipRepository;

    public DomainChampionshipService(@NotNull final ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }

    @Override
    public UUID createChampionship(@NotNull final Championship league) {
        this.championshipRepository.save(league);

        return league.getId();
    }

    @Override
    public void deleteChampionship(@NotNull final UUID id) {
        this.championshipRepository.delete(id);
    }

    @Override
    public Championship getChampionship(@NotNull final UUID id) {
        return this.championshipRepository.findById(id);
    }

	@Override
	public List<Championship> getChampionships() {
		return this.championshipRepository.findAll();
	}

    @Override
    public void updateChampionship(@NotNull final UUID id, @NotNull final Championship championship) {
        this.championshipRepository.update(id, championship);
    }
}
