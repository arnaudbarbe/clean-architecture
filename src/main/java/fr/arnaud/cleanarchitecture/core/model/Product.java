package fr.arnaud.cleanarchitecture.core.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"id", "price", "name"})
@ToString(of= {"id", "price", "name"})
public class Product {
    
	UUID id;
    Double price;
    String name;

    @JsonCreator
    public Product(@JsonProperty("id") final UUID id, @JsonProperty("price") final Double price, @JsonProperty("name") final String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

}
