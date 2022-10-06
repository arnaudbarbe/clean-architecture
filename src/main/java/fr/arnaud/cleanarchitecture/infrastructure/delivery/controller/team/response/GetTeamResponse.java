package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.response;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Team;
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
@EqualsAndHashCode(of= {"team"})
@ToString(of= {"team"})
@Builder
public class GetTeamResponse {
	@NotNull 
	Team team;
}
