package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.product.request;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of= {"product"})
@ToString(of= {"product"})
@Builder
public class CreateProductRequest {
    @NotNull 
    Product product;
}