package fr.arnaud.cleanarchitecture.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Product;

public interface ProductRepository {
    Optional<Product> findById(UUID id);

    void save(Product product);
    
    List<Product> findAll();
}