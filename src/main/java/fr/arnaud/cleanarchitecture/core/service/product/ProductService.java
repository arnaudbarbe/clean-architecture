package fr.arnaud.cleanarchitecture.core.service.product;

import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Product;

public interface ProductService {

	UUID createProduct(Product product);

}
