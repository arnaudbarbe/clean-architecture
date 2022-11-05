package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;



public record MatchDto(
        @NotNull @NotEmpty UUID id, 
		@JsonSerialize(using = LocalDateTimeSerializer.class)
		@JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		@NotNull @NotEmpty LocalDateTime when, 
		@NotNull @NotEmpty ChampionshipDto championship, 
		@NotNull @NotEmpty TeamDto homeTeam, 
		@NotNull @NotEmpty TeamDto outsideTeam, 
		int scoreHomeTeam, 
		int scoreOutsideTeam) implements Dto {

	public fr.arnaud.cleanarchitecture.core.model.Match toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.Match.builder()
				.id(this.id)
				.when(this.when)
				.championship(this.championship.toEntity())
				.homeTeam(this.homeTeam.toEntity())
				.outsideTeam(this.outsideTeam.toEntity())
				.scoreHomeTeam(this.scoreHomeTeam)
				.scoreOutsideTeam(this.scoreOutsideTeam).build();
	}
	
	public static MatchDto fromEntity(final fr.arnaud.cleanarchitecture.core.model.Match match) {
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
