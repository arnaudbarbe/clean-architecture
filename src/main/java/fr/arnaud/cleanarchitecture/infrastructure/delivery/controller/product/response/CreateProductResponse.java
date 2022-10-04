package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.product.response;

import java.util.UUID;

import javax.validation.constraints.NotNull;

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
@EqualsAndHashCode(of= {"id"})
@ToString(of= {"id"})
@Builder
public class CreateProductResponse {
	@NotNull 
	UUID id;
}
