package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString 
@EqualsAndHashCode(callSuper = true, of= {"id", "name"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChampionshipModel extends RepresentationModel<ChampionshipModel> implements Model {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id; 
	@NotNull String name; 
	@NotNull PlayerModel player; 
	@NotNull SeasonModel season; 
	@NotNull LeagueModel league;

	public fr.arnaud.cleanarchitecture.core.model.Championship toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.Championship.builder()
				.id(this.id)
				.name(this.name)
				.player(this.player.toEntity())
				.season(this.season.toEntity())
				.league(this.league.toEntity())
				.build();
	}
	
	public static ChampionshipModel fromEntity(final fr.arnaud.cleanarchitecture.core.model.Championship championship) {
		if (championship == null) {
			return null;
		} else {
			return new ChampionshipModel(championship.getId(), 
					championship.getName(), 
					PlayerModel.fromEntity(championship.getPlayer()),  
					SeasonModel.fromEntity(championship.getSeason()),
					LeagueModel.fromEntity(championship.getLeague()));
		}
	}
}
