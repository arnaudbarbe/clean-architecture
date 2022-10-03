package fr.arnaud.cleanarchitecture.core.service;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Order;
import fr.arnaud.cleanarchitecture.core.model.Product;

public interface OrderService {
    UUID createOrder(Product product);

    void addProduct(UUID id, Product product);

    void completeOrder(UUID id);

    void deleteProduct(UUID id, UUID productId);
    
    List<Order> getOrders();
}