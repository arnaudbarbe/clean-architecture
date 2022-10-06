package fr.arnaud.cleanarchitecture.infrastructure.persistence.cassandra.product;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Table;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Table
public class ProductEntity {

	@PrimaryKey
	UUID id;
	Double price;
	String name;
	LocalDateTime creationDate;

	public ProductEntity() {
		super();
	}

/*	public ProductEntity(final Product product) {
		this.id = product.getId();
		this.price = product.getPrice();
		this.name = product.getName();
		this.creationDate = product.getCreationDate();
	}

	public Product toProduct() {
		return new Product(this.id, this.price, this.name, this.creationDate);
	}*/
}