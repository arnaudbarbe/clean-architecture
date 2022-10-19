package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

public record PlayerDto(@NotNull UUID id, @NotNull String firstName, @NotNull String lastName) implements Serializable {

	public fr.arnaud.cleanarchitecture.core.entity.Player toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Player.builder().id(this.id).firstName(this.firstName).lastName(this.lastName).build();
	}
	
	public static PlayerDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Player player) {
		if (player == null) {
			return null;
		} else {
			return new PlayerDto(player.getId(), player.getFirstName(), player.getLastName());
		}
	}
}
