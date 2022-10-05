package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.league.response;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.League;
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
@EqualsAndHashCode(of= {"league"})
@ToString(of= {"league"})
@Builder
public class UpdateLeagueResponse {
	@NotNull 
	League league;
}
