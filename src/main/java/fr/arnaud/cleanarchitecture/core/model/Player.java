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
@EqualsAndHashCode(of= {"id", "firstName", "lastName"})
@ToString(of= {"id", "firstName", "lastName"})
@Builder
public class Player {

	@NotNull 
	UUID id;
	@NotNull 
    String firstName;
	@NotNull 
    String lastName;
}
