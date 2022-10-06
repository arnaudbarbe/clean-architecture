package fr.arnaud.cleanarchitecture.core.service.season;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Season;

public interface SeasonService {
	
    UUID createSeason(@NotNull Season player);

    void deleteSeason(@NotNull UUID id);
    
    Season getSeason(@NotNull UUID id);
    
    List<Season> getSeasons();

	Season updateSeason(@NotNull UUID id, @NotNull Season season);
}