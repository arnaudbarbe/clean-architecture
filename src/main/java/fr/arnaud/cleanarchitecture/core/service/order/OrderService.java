package fr.arnaud.cleanarchitecture.core.service.order;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Order;
import fr.arnaud.cleanarchitecture.core.model.Product;

public interface OrderService {
    UUID createOrder(Product product);

    void addProduct(UUID id, Product product);

    void completeOrder(UUID id);

    void removeProduct(UUID id, UUID productId);
    
    List<Order> getOrders();
}