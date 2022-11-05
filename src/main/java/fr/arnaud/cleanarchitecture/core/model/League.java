package fr.arnaud.cleanarchitecture.core.model;

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
@EqualsAndHashCode(of= {"id", "name"})
@ToString(of= {"id", "name"})
@Builder(builderClassName = "LeagueBuilder")
public class League {

	@NotNull 
	final UUID id;
	@NotNull 
	final String name;
}
