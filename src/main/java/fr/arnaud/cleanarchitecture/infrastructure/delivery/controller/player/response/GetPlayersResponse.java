package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.Player;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
public class GetPlayersResponse extends ArrayList<Player> {

	private static final long serialVersionUID = -875422354453514099L;

	public GetPlayersResponse addOrders(final List<Player> players) {
		this.addAll(players);
		return this;
	}
}
