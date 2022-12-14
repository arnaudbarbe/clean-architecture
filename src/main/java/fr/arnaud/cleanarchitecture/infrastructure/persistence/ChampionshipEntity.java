package fr.arnaud.cleanarchitecture.infrastructure.persistence;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.arnaud.cleanarchitecture.core.model.Championship;
import fr.arnaud.cleanarchitecture.core.model.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "name"})
@Entity
@Table(name = "championship")
public class ChampionshipEntity {

	@Id
	UUID id;

	String name;

	UUID playerId;

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_championship_season"), name = "seasonId", nullable = false)
	SeasonEntity season;

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_championship_league"), name = "leagueId", nullable = false)
	LeagueEntity league;

	public ChampionshipEntity() {
		super();
	}

	public ChampionshipEntity(final UUID id) {
		this.id = id;
	}

	public ChampionshipEntity(final Championship championship) {
		this.id = championship.getId();
		fromEntity(championship);
	}

	public Championship toEntity(final Player player) {
		return Championship.builder()
				.id(this.id)
				.name(this.name)
				.player(player)
				.season(this.season.toEntity())
				.league(this.league.toEntity())
				.build();
	}

	public void fromEntity(final Championship championship) {
		this.name = championship.getName();
		this.playerId = championship.getPlayer().getId();
		this.season = new SeasonEntity(championship.getSeason().getId());
		this.league = new LeagueEntity(championship.getLeague().getId());
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
		ChampionshipEntity other = (ChampionshipEntity) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}
}