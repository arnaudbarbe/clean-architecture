package fr.arnaud.cleanarchitecture.infrastructure.persistence.mongo.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Product;
import fr.arnaud.cleanarchitecture.core.repository.ProductRepository;

@Component
@Primary
public class MongoDbProductRepository implements ProductRepository {

    private final SpringDataMongoProductRepository productRepository;

    @Autowired
    public MongoDbProductRepository(final SpringDataMongoProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(final UUID id) {
        return this.productRepository.findById(id);
    }

    @Override
    public void save(final Product product) {
        this.productRepository.save(product);
    }
    
	@Override
	public List<Product> findAll() {
		return this.productRepository.findAll();
	}
}
