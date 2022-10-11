package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;



public record MatchDto(UUID id, 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		LocalDateTime when, 
		ChampionshipDto championship, TeamDto homeTeam, TeamDto outsideTeam, int scoreHomeTeam, int scoreOutsideTeam) {

	public fr.arnaud.cleanarchitecture.core.entity.Match toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Match.builder().id(this.id).when(this.when).championship(this.championship.toEntity())
				.homeTeam(this.homeTeam.toEntity()).outsideTeam(this.outsideTeam.toEntity()).scoreHomeTeam(this.scoreHomeTeam).scoreOutsideTeam(this.scoreOutsideTeam).build();
	}
	
	public static MatchDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Match match) {
		if (match == null) {
			return null;
		} else {
			return new MatchDto(match.getId(), match.getWhen(), 
					ChampionshipDto.fromEntity(match.getChampionship()), 
					TeamDto.fromEntity(match.getHomeTeam()), 
					TeamDto.fromEntity(match.getOutsideTeam()), 
					match.getScoreHomeTeam(), match.getScoreOutsideTeam());
		}
	}
}
