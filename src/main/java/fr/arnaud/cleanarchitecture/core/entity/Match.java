package fr.arnaud.cleanarchitecture.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
@EqualsAndHashCode(of= {"id"})
@ToString(of= {"id"})
@Builder(builderClassName = "MatchBuilder")
public class Match {
	
	@NotNull 
	final UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    final LocalDateTime when;
    
	@NotNull 
	final Championship championship;
	@NotNull 
	final Team homeTeam;
	@NotNull 
	final Team outsideTeam;
    
	final int scoreHomeTeam;
	final int scoreOutsideTeam;
	
}
