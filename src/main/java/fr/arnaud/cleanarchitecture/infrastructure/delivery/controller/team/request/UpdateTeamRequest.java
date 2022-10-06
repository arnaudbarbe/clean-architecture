package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.request;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Team;
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
@EqualsAndHashCode(of= {"team"})
public class UpdateTeamRequest {
    @NotNull 
    Team team;

}
