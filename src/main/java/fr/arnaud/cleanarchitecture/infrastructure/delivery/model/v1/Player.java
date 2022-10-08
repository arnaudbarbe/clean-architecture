package fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1;

import java.util.UUID;

public record Player(UUID id, String firstName, String lastName) {

	public fr.arnaud.cleanarchitecture.core.entities.Player toEntity() {
		return fr.arnaud.cleanarchitecture.core.entities.Player.builder().id(this.id).firstName(this.firstName).lastName(this.lastName).build();
	}
	
	public static Player fromEntity(final fr.arnaud.cleanarchitecture.core.entities.Player player) {
		if (player == null) {
			return null;
		} else {
			return new Player(player.getId(), player.getFirstName(), player.getLastName());
		}
	}
}
