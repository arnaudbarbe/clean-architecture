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

	public GetTeamsResponse addOrders(final List<Team> teams) {
		this.addAll(teams);
		return this;
	}
}
