package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.Championship;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
public class GetChampionshipsResponse extends ArrayList<Championship> {

	public GetChampionshipsResponse addOrders(final List<Championship> championships) {
		this.addAll(championships);
		return this;
	}
}