package fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.match;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.model.Team;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "when", "homeTeamId", "outsideTeamId", "scoreHomeTeam", "scoreOutsideTeam"})
@Document
public class MatchEntity {

	@Id
	String id;
	
    LocalDateTime when;

    String homeTeamId;

    String outsideTeamId;
    
	int scoreHomeTeam;
	int scoreOutsideTeam;

	public MatchEntity() {
		super();
	}

	public MatchEntity(final String id) {
		this.id = id;
	}

	public MatchEntity(final Match match) {
		this.id = match.getId().toString();
		fromModel(match);
	}

	public Match toModel(final Team homeTeam, final Team outsideTeam) {
		return Match.builder()
				.id(UUID.fromString(this.id))
				.when(this.when)
				.homeTeam(homeTeam)
				.outsideTeam(outsideTeam)
				.scoreHomeTeam(this.scoreHomeTeam)
				.scoreOutsideTeam(this.scoreOutsideTeam)
				.build();
	}

	public void fromModel(final Match match) {
		this.when = match.getWhen();
		this.homeTeamId = match.getHomeTeam().getId().toString();
		this.outsideTeamId = match.getOutsideTeam().getId().toString();
		this.scoreHomeTeam = match.getScoreHomeTeam();
		this.scoreOutsideTeam = match.getScoreOutsideTeam();
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