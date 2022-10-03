package fr.arnaud.cleanarchitecture.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.DomainException;

public class Order {
    private UUID id;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private BigDecimal price;

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
        this.price = this.price.add(product.getPrice());
    }

    public void removeOrder(final UUID id) {
        validateState();
        final OrderItem orderItem = getOrderItem(id);
        this.orderItems.remove(orderItem);

        this.price = this.price.subtract(orderItem.getPrice());
    }

    private OrderItem getOrderItem(final UUID id) {
        return this.orderItems.stream()
            .filter(orderItem -> orderItem.getProductId()
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

    public UUID getId() {
        return this.id;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(this.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.orderItems, this.price, this.status);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Order))
            return false;
        Order other = (Order) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.orderItems, other.orderItems) && Objects.equals(this.price, other.price) && this.status == other.status;
    }

    private Order() {
    }
}