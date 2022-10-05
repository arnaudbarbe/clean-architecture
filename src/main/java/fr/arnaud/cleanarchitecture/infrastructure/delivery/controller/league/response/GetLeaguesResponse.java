package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.league.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.League;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
public class GetLeaguesResponse extends ArrayList<League> {

	public GetLeaguesResponse addOrders(final List<League> leagues) {
		this.addAll(leagues);
		return this;
	}
}
