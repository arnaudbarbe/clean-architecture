package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.team;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.arnaud.cleanarchitecture.core.entities.Team;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.championship.ChampionshipEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "name"})
@Entity
@Table(name = "team")
public class TeamEntity {

	@Id
	UUID id;
	String name;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_championship_team"), name = "teamId", nullable = false)
	ChampionshipEntity championship;

	public TeamEntity() {
		super();
	}

	public TeamEntity(final UUID id) {
		this.id = id;
	}

	public TeamEntity(final Team team) {
		this.id = team.getId();
		fromModel(team);
	}

	public Team toModel() {
		return Team.builder()
				.id(this.id)
				.name(this.name)
				.build();
	}

	public void fromModel(final Team team) {
		this.name = team.getName();
		this.championship = new ChampionshipEntity(team.getChampionship().getId());
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
		TeamEntity other = (TeamEntity) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}