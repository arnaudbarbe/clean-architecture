package fr.arnaud.cleanarchitecture.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.exception.DomainException;

public class Order {
    private UUID id;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private Double price;

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

    public UUID getId() {
        return this.id;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Double getPrice() {
        return this.price;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(this.orderItems);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.orderItems == null) ? 0 : this.orderItems.hashCode());
		result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
		result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.orderItems == null) {
			if (other.orderItems != null)
				return false;
		} else if (!this.orderItems.equals(other.orderItems))
			return false;
		if (this.price == null) {
			if (other.price != null)
				return false;
		} else if (!this.price.equals(other.price))
			return false;
		if (this.status != other.status)
			return false;
		return true;
	}
}