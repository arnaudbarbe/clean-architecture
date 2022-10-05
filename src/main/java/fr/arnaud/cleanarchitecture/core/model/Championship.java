package fr.arnaud.cleanarchitecture.core.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"id", "name"})
@ToString(of= {"id", "name"})
@Builder
public class Championship {

	@NotNull 
	UUID id;
	@NotNull 
    String name;
	@NotNull 
    Player player;
	@NotNull 
    Team team;
	@NotNull 
    Season season;
	@NotNull 
    League league;

}
