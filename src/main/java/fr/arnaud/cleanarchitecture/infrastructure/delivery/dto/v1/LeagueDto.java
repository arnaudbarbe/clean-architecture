package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.util.UUID;

public record LeagueDto(UUID id, String name) {

	public fr.arnaud.cleanarchitecture.core.entity.League toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.League.builder().id(this.id).name(this.name).build();
	}
	
	public static LeagueDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.League league) {
		if (league == null) {
			return null;
		} else {
			return new LeagueDto(league.getId(), league.getName());
		}
	}
}
