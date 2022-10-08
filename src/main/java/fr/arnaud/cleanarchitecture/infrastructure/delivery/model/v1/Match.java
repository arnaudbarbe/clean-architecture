package fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1;

import java.time.LocalDateTime;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.entities.Championship;
import fr.arnaud.cleanarchitecture.core.entities.Team;

public record Match(UUID id, LocalDateTime when, Championship championship, Team homeTeam, Team outsideTeam, int scoreHomeTeam, int scoreOutsideTeam) {

	public fr.arnaud.cleanarchitecture.core.entities.Match toEntity() {
		return fr.arnaud.cleanarchitecture.core.entities.Match.builder().id(this.id).when(this.when).championship(this.championship)
				.homeTeam(this.homeTeam).outsideTeam(this.outsideTeam).scoreHomeTeam(this.scoreHomeTeam).scoreOutsideTeam(this.scoreOutsideTeam).build();
	}
	
	public static Match fromEntity(final fr.arnaud.cleanarchitecture.core.entities.Match match) {
		if (match == null) {
			return null;
		} else {
			return new Match(match.getId(), match.getWhen(), match.getChampionship(), match.getHomeTeam(), match.getOutsideTeam(), match.getScoreHomeTeam(), match.getScoreOutsideTeam());
		}
	}
}
