package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.league.response;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"id"})
@ToString(of= {"id"})
@Builder
public class CreateLeagueResponse {
	@NotNull 
	UUID id;
}
