package fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1;

import java.util.UUID;

public record Team(UUID id, String name) {

	public fr.arnaud.cleanarchitecture.core.entities.Team toEntity() {
		return fr.arnaud.cleanarchitecture.core.entities.Team.builder().id(this.id).name(this.name).build();
	}
	
	public static Team fromEntity(final fr.arnaud.cleanarchitecture.core.entities.Team team) {
		if (team == null) {
			return null;
		} else {
			return new Team(team.getId(), team.getName());
		}
	}
}