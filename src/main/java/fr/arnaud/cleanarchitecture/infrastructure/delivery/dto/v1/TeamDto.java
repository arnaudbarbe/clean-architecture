package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.util.UUID;

public record TeamDto(UUID id, String name, ChampionshipDto championship) {

	public fr.arnaud.cleanarchitecture.core.entity.Team toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Team.builder().id(this.id).name(this.name).championship(this.championship.toEntity()).build();
	}
	
	public static TeamDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Team team) {
		if (team == null) {
			return null;
		} else {
			return new TeamDto(team.getId(), team.getName(), ChampionshipDto.fromEntity(team.getChampionship()));
		}
	}
}