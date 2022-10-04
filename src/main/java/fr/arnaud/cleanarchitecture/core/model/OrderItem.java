package fr.arnaud.cleanarchitecture.core.model;

import java.math.BigDecimal;

public class OrderItem {
    private Product product;
    private BigDecimal price;

    public OrderItem(final Product product) {
        this.product = product;
        this.price = product.getPrice();
    }

    public Product getProduct() {
        return this.product;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
		result = prime * result + ((this.product == null) ? 0 : this.product.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (this.price == null) {
			if (other.price != null)
				return false;
		} else if (!this.price.equals(other.price))
			return false;
		if (this.product == null) {
			if (other.product != null)
				return false;
		} else if (!this.product.equals(other.product))
			return false;
		return true;
	}
}