package fr.arnaud.cleanarchitecture.core.service.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Product;

public interface ProductService {

	UUID createProduct(Product product);

    List<Product> getProducts();

	void deleteByCreationDateLessThan(LocalDateTime localDateTime);
}
