package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record TeamDto(
		@NotNull @NotEmpty UUID id, 
		@NotNull @NotEmpty String name, 
		@NotNull @NotEmpty ChampionshipDto championship) implements Dto {

	public fr.arnaud.cleanarchitecture.core.model.Team toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.Team.builder()
				.id(this.id)
				.name(this.name)
				.championship(this.championship.toEntity()).build();
	}
	
	public static TeamDto fromEntity(final fr.arnaud.cleanarchitecture.core.model.Team team) {
		if (team == null) {
			return null;
		} else {
			return new TeamDto(
					team.getId(), 
					team.getName(), 
					ChampionshipDto.fromEntity(team.getChampionship()));
		}
	}
}
