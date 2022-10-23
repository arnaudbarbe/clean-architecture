package fr.arnaud.cleanarchitecture.core.service.player;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entity.Player;
import fr.arnaud.cleanarchitecture.core.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;

    public DomainPlayerService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UUID createPlayer(final Player player) {
        log.info("Create player {}", player);
        this.playerRepository.save(player);

        return player.getId();
    }

    @Override
    public void deletePlayer(final UUID id) {
        log.info("Delete player {}", id);
        this.playerRepository.delete(id);
    }

    @Override
    public Player getPlayer(final UUID id) {
        log.info("Get player {}", id);
        return this.playerRepository.findById(id);
    }

	@Override
	public List<Player> getPlayers() {
        log.info("Get players");
		return this.playerRepository.findAll();
	}

	@Override
	public void updatePlayer(final UUID id, final Player player) {
        log.info("Update player {} {}", id, player);
        this.playerRepository.update(id, player);
	}
}
