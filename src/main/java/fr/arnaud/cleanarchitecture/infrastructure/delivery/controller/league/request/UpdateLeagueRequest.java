package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.league.request;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.League;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"league"})
public class UpdateLeagueRequest {
    @NotNull 
    League league;

}
