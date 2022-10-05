package fr.arnaud.cleanarchitecture.core.service.player;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Player;

public interface PlayerService {
	
    UUID createPlayer(Player player);

    void deletePlayer(UUID id);
    
    Player getPlayer(UUID id);
    
    List<Player> getPlayers();
}