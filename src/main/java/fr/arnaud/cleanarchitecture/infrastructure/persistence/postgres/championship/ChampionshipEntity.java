package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.championship;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.arnaud.cleanarchitecture.core.entities.Championship;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.league.LeagueEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.player.PlayerEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.season.SeasonEntity;
import fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.team.TeamEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of= {"id", "name"})
@Entity
@Table
public class ChampionshipEntity {

	@Id
	UUID id;

	String name;

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_championship_player"), name = "playerId", nullable = false)
	PlayerEntity player;

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_championship_team"), name = "teamId", nullable = false)
	TeamEntity team;

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
		fromModel(championship);
	}

	public Championship toModel() {
		return Championship.builder()
				.id(this.id)
				.name(this.name)
				.team(this.team.toModel())
				.player(this.player.toModel())
				.season(this.season.toModel())
				.league(this.league.toModel())
				.build();
	}

	public void fromModel(final Championship championship) {
		this.name = championship.getName();
		this.player = new PlayerEntity(championship.getPlayer().getId());
		this.team = new TeamEntity(championship.getTeam().getId());
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