package fr.arnaud.cleanarchitecture.core.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"product", "price"})
@ToString(of= {"product", "price"})
public class OrderItem {
	
    Product product;
    Double price;

    public OrderItem(final Product product) {
        this.product = product;
        this.price = product.getPrice();
    }
}