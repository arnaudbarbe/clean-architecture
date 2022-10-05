package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Player;

public interface PlayerRepository {
	
    Optional<Player> findById(UUID id);

    void save(Player player);
    
    List<Player> findAll();

    void delete(UUID id);

    Player update(UUID id, Player player);
}