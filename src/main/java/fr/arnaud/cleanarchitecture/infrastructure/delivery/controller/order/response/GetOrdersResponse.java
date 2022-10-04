package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.response;

import java.util.ArrayList;
import java.util.List;

import fr.arnaud.cleanarchitecture.core.model.Order;

public class GetOrdersResponse extends ArrayList<Order> {
	
	private static final long serialVersionUID = 1L;

    public GetOrdersResponse(final List<Order> orders) {
        super(orders);
    }
}
