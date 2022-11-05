package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record LeagueDto(
		@NotNull @NotEmpty UUID id, 
		@NotNull @NotEmpty String name) implements Dto {

	public fr.arnaud.cleanarchitecture.core.model.League toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.League.builder()
				.id(this.id)
				.name(this.name).build();
	}
	
	public static LeagueDto fromEntity(final fr.arnaud.cleanarchitecture.core.model.League league) {
		if (league == null) {
			return null;
		} else {
			return new LeagueDto(
					league.getId(), 
					league.getName());
		}
	}
}
