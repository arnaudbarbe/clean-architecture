package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Product;
import fr.arnaud.cleanarchitecture.core.repository.ProductRepository;

@Component
public class CassandraDbProductRepository implements ProductRepository {

    private final SpringDataCassandraProductRepository productRepository;

    @Autowired
    public CassandraDbProductRepository(final SpringDataCassandraProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(final UUID id) {
        Optional<ProductEntity> productEntity = this.productRepository.findById(id);
        if (productEntity.isPresent()) {
            return Optional.of(productEntity.get()
                .toProduct());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(final Product product) {
        this.productRepository.save(new ProductEntity(product));
    }

	@Override
	public List<Product> findAll() {

		return this.productRepository.findAll()
		.stream()
		.map(ProductEntity::toProduct).toList();
	}
}
