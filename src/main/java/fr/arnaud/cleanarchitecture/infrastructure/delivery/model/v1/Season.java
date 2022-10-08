package fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public record Season(UUID id, String name, 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		LocalDateTime startDate, 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		LocalDateTime endDate) {
	
	public fr.arnaud.cleanarchitecture.core.entities.Season toEntity() {
		return fr.arnaud.cleanarchitecture.core.entities.Season.builder().id(this.id).name(this.name).startDate(this.startDate).endDate(this.endDate).build();
	}
	
	public static Season fromEntity(final fr.arnaud.cleanarchitecture.core.entities.Season season) {
		if (season == null) {
			return null;
		} else {
			return new Season(season.getId(), season.getName(), season.getStartDate(), season.getEndDate());
		}
	}
}
