package fr.arnaud.cleanarchitecture.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import fr.arnaud.cleanarchitecture.core.model.Championship;
import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.model.Team;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "when", "homeTeamId", "outsideTeamId", "scoreHomeTeam", "scoreOutsideTeam"})
@Document(collection = "match")
public class MatchEntity {

	@Id
	UUID id;
	
    LocalDateTime when;

    String homeTeamId;

    String outsideTeamId;
    
	int scoreHomeTeam;
	int scoreOutsideTeam;

	UUID championshipId;

	public MatchEntity() {
		super();
	}

	public MatchEntity(final UUID id) {
		this.id = id;
	}

	public MatchEntity(final Match match) {
		this.id = match.getId();
		fromEntity(match);
	}

	public Match toEntity(final Team homeTeam, final Team outsideTeam, final Championship championship) {
		return Match.builder()
				.id(this.id)
				.when(this.when)
				.scoreHomeTeam(this.scoreHomeTeam)
				.scoreOutsideTeam(this.scoreOutsideTeam)
				.homeTeam(homeTeam)
				.outsideTeam(outsideTeam)
				.championship(championship)
				.build();
	}

	public void fromEntity(final Match match) {
		this.when = match.getWhen();
		this.scoreHomeTeam = match.getScoreHomeTeam();
		this.scoreOutsideTeam = match.getScoreOutsideTeam();
		this.homeTeamId = match.getHomeTeam().getId().toString();
		this.outsideTeamId = match.getOutsideTeam().getId().toString();
		this.championshipId = match.getChampionship().getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchEntity other = (MatchEntity) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}