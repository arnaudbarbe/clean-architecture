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
public class TeamModel extends RepresentationModel<TeamModel> implements Model {

	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id;
	@NotNull String name;
	@NotNull ChampionshipModel championship;

	public fr.arnaud.cleanarchitecture.core.model.Team toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.Team.builder()
				.id(this.id)
				.name(this.name)
				.championship(this.championship.toEntity()).build();
	}
	
	public static TeamModel fromEntity(final fr.arnaud.cleanarchitecture.core.model.Team team) {
		if (team == null) {
			return null;
		} else {
			return new TeamModel(
					team.getId(), 
					team.getName(), 
					ChampionshipModel.fromEntity(team.getChampionship()));
		}
	}
}
