package fr.arnaud.cleanarchitecture.core.service.championship;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.entities.Championship;

public interface ChampionshipService {
	
    UUID createChampionship(@NotNull Championship championship);

    void deleteChampionship(@NotNull UUID id);
    
    Championship getChampionship(@NotNull UUID id);
    
    List<Championship> getChampionships();

    void updateChampionship(@NotNull UUID id, @NotNull Championship championship);
}