package fr.arnaud.cleanarchitecture.core.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"id", "price", "name", "creationDate"})
@ToString(of= {"id", "price", "name", "creationDate"})
public class Product {
    
	UUID id;
    Double price;
    String name;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime creationDate;

    @JsonCreator
    public Product(
    		@JsonProperty("id") final UUID id, 
    		@JsonProperty("price") final Double price, 
    		@JsonProperty("name") final String name, 
    		@JsonProperty("creationDate") final LocalDateTime creationDate) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.creationDate = creationDate;
    }
    
    public Product(final UUID id, 
    		final Double price) {
        this.id = id;
        this.price = price;
    }

}
