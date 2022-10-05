package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
public class GetOrdersResponse extends ArrayList<Order> {

	public GetOrdersResponse addOrders(final List<Order> orders) {
		this.addAll(orders);
		return this;
	}
}
