package fr.arnaud.cleanarchitecture.core.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"id", "name", "startDate", "endDate"})
@ToString(of= {"id", "name", "startDate", "endDate"})
@Builder(builderClassName = "SeasonBuilder")
public class Season {
	
	@NotNull 
	final UUID id;
	@NotNull 
	final String name;
	@NotNull 
    final LocalDateTime startDate;
	@NotNull 
    final LocalDateTime endDate;

}
