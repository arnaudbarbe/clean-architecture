package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entities.Player;

public interface PlayerRepository {
	
    Player findById(UUID id);

    void save(Player player);
    
    List<Player> findAll();

    void delete(UUID id);

    void update(UUID id, Player player);
}