package fr.arnaud.cleanarchitecture.infrastructure.persistence.postgres.product;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.arnaud.cleanarchitecture.core.model.Product;

@Entity
@Table
public class ProductEntity {

	@Id
	UUID id;
	Double price;
	String name;
	LocalDateTime creationDate;

	public ProductEntity() {
	}

	public ProductEntity(final Product product) {
		this.id = product.getId();
		this.price = product.getPrice();
		this.name = product.getName();
		this.creationDate = product.getCreationDate();
	}

	public Product toProduct() {
		return new Product(this.id, this.price, this.name, this.creationDate);
	}
}