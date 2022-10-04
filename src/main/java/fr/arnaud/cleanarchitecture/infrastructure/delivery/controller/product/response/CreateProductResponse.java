package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.product.response;

import java.util.UUID;

public class CreateProductResponse {
    private final UUID id;

    public CreateProductResponse(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
