package fr.arnaud.cleanarchitecture.core.entities;

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
@FieldDefaults(level=AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode(of= {"id", "name"})
@ToString(of= {"id", "name"})
@Builder(builderClassName = "ChampionshipBuilder")
public class Championship {

	@NotNull 
	final UUID id;
	@NotNull 
	final String name;
	@NotNull 
	final Player player;
	@NotNull 
	final Season season;
	@NotNull 
	final League league;

}
