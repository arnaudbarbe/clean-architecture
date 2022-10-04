package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.order;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import fr.arnaud.cleanarchitecture.core.model.OrderItem;
import fr.arnaud.cleanarchitecture.core.model.Product;

@UserDefinedType
public class OrderItemEntity {

    private UUID productId;
    private Double price;

    public OrderItemEntity() {
    }

    public OrderItemEntity(final OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.price = orderItem.getPrice();
    }

    public OrderItem toOrderItem() {
        return new OrderItem(new Product(this.productId, this.price, ""));
    }

    public UUID getProductId() {
        return this.productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }
}