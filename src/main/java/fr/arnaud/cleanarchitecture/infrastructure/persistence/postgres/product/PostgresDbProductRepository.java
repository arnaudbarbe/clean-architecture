package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Product;
import fr.arnaud.cleanarchitecture.core.repository.ProductRepository;

@Component("PostgresDbProductRepository")
public class PostgresDbProductRepository implements ProductRepository {

    private final SpringDataPostgresProductRepository productRepository;

    @Autowired
    public PostgresDbProductRepository(final SpringDataPostgresProductRepository productRepository) {
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

		return StreamSupport.stream(this.productRepository.findAll().spliterator(), false)
		.map(ProductEntity::toProduct).toList();
	}

	@Override
	public void deleteByCreationDateLessThan(final LocalDateTime localDateTime) {
		this.productRepository.deleteByCreationDateLessThan(localDateTime);
	}
}
