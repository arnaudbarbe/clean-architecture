package fr.arnaud.cleanarchitecture.core.service.product;

import java.util.List;
import java.util.UUID;

import fr.arnaud.cleanarchitecture.core.model.Product;
import fr.arnaud.cleanarchitecture.core.repository.ProductRepository;

public class DomainProductService implements ProductService {

    private final ProductRepository productRepository;

    public DomainProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public UUID createProduct(final Product product) {
        this.productRepository.save(product);

        return product.getId();
    }

	@Override
	public List<Product> getProducts() {
		return this.productRepository.findAll();
	}
}
