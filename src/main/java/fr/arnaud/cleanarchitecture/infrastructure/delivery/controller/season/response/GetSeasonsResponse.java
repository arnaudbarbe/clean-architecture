package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.Season;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
public class GetSeasonsResponse extends ArrayList<Season> {

	private static final long serialVersionUID = 1197019700903831475L;

	public GetSeasonsResponse addOrders(final List<Season> seasons) {
		this.addAll(seasons);
		return this;
	}
}
