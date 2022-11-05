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

public record SeasonDto(
        @NotNull @NotEmpty UUID id, 
        @NotNull @NotEmpty String name, 
	    @JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		@NotNull @NotEmpty LocalDateTime startDate, 
	    @JsonSerialize(using = LocalDateTimeSerializer.class)
	    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
		@NotNull @NotEmpty LocalDateTime endDate) implements Dto {
	
	public fr.arnaud.cleanarchitecture.core.model.Season toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.Season.builder()
				.id(this.id)
				.name(this.name)
				.startDate(this.startDate)
				.endDate(this.endDate).build();
	}
	
	public static SeasonDto fromEntity(final fr.arnaud.cleanarchitecture.core.model.Season season) {
		if (season == null) {
			return null;
		} else {
			return new SeasonDto(
					season.getId(), 
					season.getName(), 
					season.getStartDate(), 
					season.getEndDate());
		}
	}
}
