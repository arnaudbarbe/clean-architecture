package fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1;

import java.util.UUID;

public record League(UUID id, String name) {

	public fr.arnaud.cleanarchitecture.core.entities.League toEntity() {
		return fr.arnaud.cleanarchitecture.core.entities.League.builder().id(this.id).name(this.name).build();
	}
	
	public static League fromEntity(final fr.arnaud.cleanarchitecture.core.entities.League league) {
		if (league == null) {
			return null;
		} else {
			return new League(league.getId(), league.getName());
		}
	}
}
