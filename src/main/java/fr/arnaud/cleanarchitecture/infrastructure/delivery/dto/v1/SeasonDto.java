package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public record SeasonDto(@NotNull UUID id, @NotNull String name, 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		@NotNull LocalDateTime startDate, 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		@NotNull LocalDateTime endDate) {
	
	public fr.arnaud.cleanarchitecture.core.entity.Season toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Season.builder().id(this.id).name(this.name).startDate(this.startDate).endDate(this.endDate).build();
	}
	
	public static SeasonDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Season season) {
		if (season == null) {
			return null;
		} else {
			return new SeasonDto(season.getId(), season.getName(), season.getStartDate(), season.getEndDate());
		}
	}
}
