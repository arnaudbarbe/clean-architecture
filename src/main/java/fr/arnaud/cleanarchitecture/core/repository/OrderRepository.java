package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Order;

public interface OrderRepository {
    Optional<Order> findById(UUID id);

    void save(Order order);
    
    List<Order> findAll();
}