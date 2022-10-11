package fr.arnaud.cleanarchitecture.core.service.player;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.entity.Player;

public interface PlayerService {
	
    UUID createPlayer(@NotNull Player player);

    void deletePlayer(@NotNull UUID id);
    
    Player getPlayer(@NotNull UUID id);
    
    List<Player> getPlayers();

    void updatePlayer(@NotNull UUID id, @NotNull Player player);
}