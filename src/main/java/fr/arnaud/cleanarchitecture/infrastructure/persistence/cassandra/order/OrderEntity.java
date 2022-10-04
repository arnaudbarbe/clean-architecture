package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.order;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import fr.arnaud.cleanarchitecture.core.model.Order;
import fr.arnaud.cleanarchitecture.core.model.OrderItem;
import fr.arnaud.cleanarchitecture.core.model.OrderStatus;
import fr.arnaud.cleanarchitecture.core.model.Product;

public class OrderEntity {

    @PrimaryKey
    private UUID id;
    private OrderStatus status;
    private List<OrderItemEntity> orderItemEntities;
    private Double price;

    public OrderEntity(final UUID id, final OrderStatus status, final List<OrderItemEntity> orderItemEntities, final Double price) {
        this.id = id;
        this.status = status;
        this.orderItemEntities = orderItemEntities;
        this.price = price;
    }

    public OrderEntity() {
    }

    public OrderEntity(final Order order) {
        this.id = order.getId();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.orderItemEntities = order.getOrderItems()
            .stream()
            .map(OrderItemEntity::new)
            .toList();

    }

    public Order toOrder() {
        List<OrderItem> orderItems = this.orderItemEntities.stream()
            .map(OrderItemEntity::toOrderItem)
            .toList();
        List<Product> namelessProducts = orderItems.stream()
            .map(OrderItem::getProduct)
            .collect(Collectors.toList());
        Order order = new Order(this.id, namelessProducts.remove(0));
        namelessProducts.forEach(order::addOrder);
        if (this.status == OrderStatus.COMPLETED) {
            order.complete();
        }
        return order;
    }

    public UUID getId() {
        return this.id;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public List<OrderItemEntity> getOrderItems() {
        return this.orderItemEntities;
    }

    public Double getPrice() {
        return this.price;
    }
}