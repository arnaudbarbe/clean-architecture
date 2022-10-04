package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.product;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPostgresProductRepository extends CrudRepository<ProductEntity, UUID> {
	void deleteByCreationDateLessThan(LocalDateTime localDateTime);
}