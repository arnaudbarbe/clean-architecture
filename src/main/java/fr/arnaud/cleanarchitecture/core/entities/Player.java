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
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"id", "firstName", "lastName"})
@ToString(of= {"id", "firstName", "lastName"})
@Builder(builderClassName = "PlayerBuilder")
public class Player {

	@NotNull 
	final UUID id;
	@NotNull 
	final String firstName;
	@NotNull 
	final String lastName;
}
