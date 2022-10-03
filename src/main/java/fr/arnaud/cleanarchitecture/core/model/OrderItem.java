package fr.arnaud.cleanarchitecture.core.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class OrderItem {
    private UUID productId;
    private BigDecimal price;

    public OrderItem(final Product product) {
        this.productId = product.getId();
        this.price = product.getPrice();
    }

    public UUID getProductId() {
        return this.productId;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    private OrderItem() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(this.productId, orderItem.productId) && Objects.equals(this.price, orderItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.productId, this.price);
    }
}