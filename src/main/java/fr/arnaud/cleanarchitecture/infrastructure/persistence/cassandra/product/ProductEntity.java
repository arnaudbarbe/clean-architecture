package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.product;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import fr.arnaud.cleanarchitecture.core.model.Product;

public class ProductEntity {

	@PrimaryKey
	private UUID id;
	private BigDecimal price;
	private String name;

	public ProductEntity() {
	}

	public ProductEntity(final Product product) {
		this.id = product.getId();
		this.price = product.getPrice();
		this.name = product.getName();
	}

	public Product toProduct() {
		return new Product(this.id, this.price, this.name);
	}

	public UUID getId() {
		return this.id;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public String getName() {
		return this.name;
	}

}