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
@EqualsAndHashCode(callSuper = true, of= {"id", "name", "startDate", "endDate"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeasonDto extends RepresentationModel<SeasonDto> implements Dto {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id;
	@NotNull String name;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
	@NotNull LocalDateTime startDate; 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") 
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@NotNull LocalDateTime endDate;
	
	public fr.arnaud.cleanarchitecture.core.entity.Season toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Season.builder()
				.id(this.id)
				.name(this.name)
				.startDate(this.startDate)
				.endDate(this.endDate).build();
	}
	
	public static SeasonDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Season season) {
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
