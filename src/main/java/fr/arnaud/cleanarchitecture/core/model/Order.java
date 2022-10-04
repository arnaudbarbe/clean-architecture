package fr.arnaud.cleanarchitecture.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.DomainException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"id", "status", "orderItems", "price"})
@ToString(of= {"id", "status", "orderItems", "price"})
public class Order {
    UUID id;
    OrderStatus status;
    List<OrderItem> orderItems;
    Double price;

    public Order(final UUID id, final Product product) {
        this.id = id;
        this.orderItems = new ArrayList<>(Collections.singletonList(new OrderItem(product)));
        this.status = OrderStatus.CREATED;
        this.price = product.getPrice();
    }

    public void complete() {
        validateState();
        this.status = OrderStatus.COMPLETED;
    }

    public void addOrder(final Product product) {
        validateState();
        validateProduct(product);
        this.orderItems.add(new OrderItem(product));
        this.price = product.getPrice();
    }

    public void removeOrder(final UUID id) {
        validateState();
        final OrderItem orderItem = getOrderItem(id);
        this.orderItems.remove(orderItem);

        this.price = this.price - orderItem.getPrice();
    }

    private OrderItem getOrderItem(final UUID id) {
        return this.orderItems.stream()
            .filter(orderItem -> orderItem.getProduct().getId()
                .equals(id))
            .findFirst()
            .orElseThrow(() -> new DomainException("Product with " + id + " doesn't exist."));
    }

    private void validateState() {
        if (OrderStatus.COMPLETED.equals(this.status)) {
            throw new DomainException("The order is in completed state.");
        }
    }

    private void validateProduct(final Product product) {
        if (product == null) {
            throw new DomainException("The product cannot be null.");
        }
    }
}