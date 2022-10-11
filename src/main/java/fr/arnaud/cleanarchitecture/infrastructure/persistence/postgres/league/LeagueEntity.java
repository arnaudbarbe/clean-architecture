package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.league;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.arnaud.cleanarchitecture.core.entity.League;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "name"})
@Entity
@Table(name = "league")
public class LeagueEntity {

	@Id
	UUID id;
	String name;

	public LeagueEntity() {
		super();
	}

	public LeagueEntity(final UUID id) {
		this.id = id;
	}

	public LeagueEntity(final League league) {
		this.id = league.getId();
		fromEntity(league);
	}

	public League toEntity() {
		return League.builder()
				.id(this.id)
				.name(this.name)
				.build();
	}

	public void fromEntity(final League league) {
		this.name = league.getName();
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
		LeagueEntity other = (LeagueEntity) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}