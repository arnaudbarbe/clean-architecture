package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public record ChampionshipDto(
		@NotNull @NotEmpty UUID id, 
		@NotNull @NotEmpty String name, 
		@NotNull @NotEmpty PlayerDto player, 
		@NotNull @NotEmpty SeasonDto season, 
		@NotNull @NotEmpty LeagueDto league)  implements Dto {

	public fr.arnaud.cleanarchitecture.core.model.Championship toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.Championship.builder()
				.id(this.id)
				.name(this.name)
				.player(this.player.toEntity())
				.season(this.season.toEntity())
				.league(this.league.toEntity())
				.build();
	}
	
	public static ChampionshipDto fromEntity(final fr.arnaud.cleanarchitecture.core.model.Championship championship) {
		if (championship == null) {
			return null;
		} else {
			return new ChampionshipDto(championship.getId(), 
					championship.getName(), 
					PlayerDto.fromEntity(championship.getPlayer()),  
					SeasonDto.fromEntity(championship.getSeason()),
					LeagueDto.fromEntity(championship.getLeague()));
		}
	}
}
