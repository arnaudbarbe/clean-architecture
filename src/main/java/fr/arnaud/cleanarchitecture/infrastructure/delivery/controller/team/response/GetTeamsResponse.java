package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
public class GetTeamsResponse extends ArrayList<Team> {

	private static final long serialVersionUID = 4159659938198265637L;

	public GetTeamsResponse addTeams(final List<Team> teams) {
		this.addAll(teams);
		return this;
	}
}
