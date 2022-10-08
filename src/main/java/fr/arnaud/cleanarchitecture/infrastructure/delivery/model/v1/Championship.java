package fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1;

import java.util.UUID;


public record Championship(UUID id, String name, Player player, Season season, League league) {

	public fr.arnaud.cleanarchitecture.core.entities.Championship toEntity() {
		return fr.arnaud.cleanarchitecture.core.entities.Championship.builder().id(this.id).name(this.name)
				.player(this.player.toEntity())
				.season(this.season.toEntity())
				.league(this.league.toEntity())
				.build();
	}
	
	public static Championship fromEntity(final fr.arnaud.cleanarchitecture.core.entities.Championship championship) {
		if (championship == null) {
			return null;
		} else {
			return new Championship(championship.getId(), championship.getName(), 
					Player.fromEntity(championship.getPlayer()),  
					Season.fromEntity(championship.getSeason()),
					League.fromEntity(championship.getLeague()));
		}
	}
}
