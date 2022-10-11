package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.util.UUID;


public record ChampionshipDto(UUID id, String name, PlayerDto player, SeasonDto season, LeagueDto league) {

	public fr.arnaud.cleanarchitecture.core.entity.Championship toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Championship.builder().id(this.id).name(this.name)
				.player(this.player.toEntity())
				.season(this.season.toEntity())
				.league(this.league.toEntity())
				.build();
	}
	
	public static ChampionshipDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Championship championship) {
		if (championship == null) {
			return null;
		} else {
			return new ChampionshipDto(championship.getId(), championship.getName(), 
					PlayerDto.fromEntity(championship.getPlayer()),  
					SeasonDto.fromEntity(championship.getSeason()),
					LeagueDto.fromEntity(championship.getLeague()));
		}
	}
}
