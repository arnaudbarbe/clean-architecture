package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.product;

import org.springframework.stereotype.Component;

@Component("CassandraDbProductRepository")
public class CassandraDbProductRepository /*implements ProductRepository*/ {

 /*   private final SpringDataCassandraProductRepository productRepository;

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

	@Override
	public void deleteByCreationDateLessThan(final LocalDateTime localDateTime) {
		this.productRepository.deleteByCreationDateLessThan(localDateTime);
	}*/
}
