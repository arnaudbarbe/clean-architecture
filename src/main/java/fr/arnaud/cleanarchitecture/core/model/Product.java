package fr.arnaud.cleanarchitecture.core.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private final UUID id;
    private final BigDecimal price;
    private final String name;

    @JsonCreator
    public Product(@JsonProperty("id") final UUID id, @JsonProperty("price") final BigDecimal price, @JsonProperty("name") final String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public UUID getId() {
        return this.id;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
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
		Product other = (Product) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.price == null) {
			if (other.price != null)
				return false;
		} else if (!this.price.equals(other.price))
			return false;
		return true;
	}
}
