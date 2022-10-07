package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.Match;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
public class GetMatchsResponse extends ArrayList<Match> {

	private static final long serialVersionUID = 999335269974135989L;

	public GetMatchsResponse addOrders(final List<Match> matchs) {
		this.addAll(matchs);
		return this;
	}
}