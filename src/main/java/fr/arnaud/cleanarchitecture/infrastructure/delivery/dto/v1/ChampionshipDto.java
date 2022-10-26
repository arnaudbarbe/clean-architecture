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
public class ChampionshipDto extends RepresentationModel<ChampionshipDto> implements Dto {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id; 
	@NotNull String name; 
	@NotNull PlayerDto player; 
	@NotNull SeasonDto season; 
	@NotNull LeagueDto league;

	public fr.arnaud.cleanarchitecture.core.entity.Championship toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Championship.builder()
				.id(this.id)
				.name(this.name)
				.player(this.player.toEntity())
				.season(this.season.toEntity())
				.league(this.league.toEntity())
				.build();
	}
	
	public static ChampionshipDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Championship championship) {
		if (championship == null) {
			return null;
		} else {
			return new ChampionshipDto(championship.getId(), 
					championship.getName(), 
					PlayerDto.fromEntity(championship.getPlayer()),  
					SeasonDto.fromEntity(championship.getSeason()),
					LeagueDto.fromEntity(championship.getLeague()));
		}
	}
}
