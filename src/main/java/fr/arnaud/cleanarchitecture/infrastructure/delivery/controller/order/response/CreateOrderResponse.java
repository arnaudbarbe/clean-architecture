package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.order.response;

import java.util.UUID;

public class CreateOrderResponse {
    private final UUID id;

    public CreateOrderResponse(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
