package fr.arnaud.cleanarchitecture.core.service.player;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Player;
import fr.arnaud.cleanarchitecture.core.repository.PlayerRepository;

public class DomainPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;

    public DomainPlayerService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UUID createPlayer(@NotNull final Player player) {
        this.playerRepository.save(player);

        return player.getId();
    }

    @Override
    public void deletePlayer(@NotNull final UUID id) {
        this.playerRepository.delete(id);
    }

    @Override
    public Player getPlayer(@NotNull final UUID id) {
        return this.playerRepository.findById(id);
    }

	@Override
	public List<Player> getPlayers() {
		return this.playerRepository.findAll();
	}

	@Override
	public void updatePlayer(@NotNull final UUID id, @NotNull final Player player) {
        this.playerRepository.update(id, player);
	}
}
