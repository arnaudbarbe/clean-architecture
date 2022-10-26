package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

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
public class TeamDto extends RepresentationModel<TeamDto> implements Dto {

	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id;
	@NotNull String name;
	@NotNull ChampionshipDto championship;

	public fr.arnaud.cleanarchitecture.core.entity.Team toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Team.builder()
				.id(this.id)
				.name(this.name)
				.championship(this.championship.toEntity()).build();
	}
	
	public static TeamDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Team team) {
		if (team == null) {
			return null;
		} else {
			return new TeamDto(
					team.getId(), 
					team.getName(), 
					ChampionshipDto.fromEntity(team.getChampionship()));
		}
	}
}
