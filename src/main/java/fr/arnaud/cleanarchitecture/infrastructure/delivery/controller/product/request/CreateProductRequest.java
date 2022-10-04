package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.product.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.arnaud.cleanarchitecture.core.model.Product;

public class CreateProductRequest {
    @NotNull 
    private Product product;

    @JsonCreator
    public CreateProductRequest(@JsonProperty("product") @NotNull final Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }
}