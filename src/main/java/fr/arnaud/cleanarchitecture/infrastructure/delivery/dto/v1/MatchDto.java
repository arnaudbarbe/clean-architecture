package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString 
@EqualsAndHashCode(callSuper = true, of= {"id"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchDto extends RepresentationModel<MatchDto> implements Dto {
		
	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
	@NotNull LocalDateTime when;
	@NotNull ChampionshipDto championship; 
	@NotNull TeamDto homeTeam;
	@NotNull TeamDto outsideTeam;
	int scoreHomeTeam;
	int scoreOutsideTeam;

	public fr.arnaud.cleanarchitecture.core.entity.Match toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Match.builder()
				.id(this.id)
				.when(this.when)
				.championship(this.championship.toEntity())
				.homeTeam(this.homeTeam.toEntity())
				.outsideTeam(this.outsideTeam.toEntity())
				.scoreHomeTeam(this.scoreHomeTeam)
				.scoreOutsideTeam(this.scoreOutsideTeam).build();
	}
	
	public static MatchDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Match match) {
		if (match == null) {
			return null;
		} else {
			return new MatchDto(match.getId(), 
					match.getWhen(), 
					ChampionshipDto.fromEntity(match.getChampionship()), 
					TeamDto.fromEntity(match.getHomeTeam()), 
					TeamDto.fromEntity(match.getOutsideTeam()), 
					match.getScoreHomeTeam(), 
					match.getScoreOutsideTeam());
		}
	}
}
