package fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;



public record Match(UUID id, 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		LocalDateTime when, 
		Championship championship, Team homeTeam, Team outsideTeam, int scoreHomeTeam, int scoreOutsideTeam) {

	public fr.arnaud.cleanarchitecture.core.entities.Match toEntity() {
		return fr.arnaud.cleanarchitecture.core.entities.Match.builder().id(this.id).when(this.when).championship(this.championship.toEntity())
				.homeTeam(this.homeTeam.toEntity()).outsideTeam(this.outsideTeam.toEntity()).scoreHomeTeam(this.scoreHomeTeam).scoreOutsideTeam(this.scoreOutsideTeam).build();
	}
	
	public static Match fromEntity(final fr.arnaud.cleanarchitecture.core.entities.Match match) {
		if (match == null) {
			return null;
		} else {
			return new Match(match.getId(), match.getWhen(), 
					Championship.fromEntity(match.getChampionship()), 
					Team.fromEntity(match.getHomeTeam()), 
					Team.fromEntity(match.getOutsideTeam()), 
					match.getScoreHomeTeam(), match.getScoreOutsideTeam());
		}
	}
}
