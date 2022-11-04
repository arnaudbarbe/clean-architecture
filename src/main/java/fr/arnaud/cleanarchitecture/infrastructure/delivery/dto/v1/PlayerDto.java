package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record PlayerDto(
		@NotNull @NotEmpty UUID id, 
		@NotNull @NotEmpty String firstName, 
		@NotNull @NotEmpty String lastName) implements Dto {

	public fr.arnaud.cleanarchitecture.core.entity.Player toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Player.builder()
				.id(this.id)
				.firstName(this.firstName)
				.lastName(this.lastName).build();
	}
	
	public static PlayerDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Player player) {
		if (player == null) {
			return null;
		} else {
			return new PlayerDto(
					player.getId(), 
					player.getFirstName(), 
					player.getLastName());
		}
	}
}
