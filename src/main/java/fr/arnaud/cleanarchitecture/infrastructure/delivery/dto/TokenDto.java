package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto;

import javax.validation.constraints.NotNull;

public record TokenDto(
		@NotNull String currentToken, 
		@NotNull String refreshToken,
		@NotNull Long expiresIn, 
		@NotNull Long refreshExpiresIn) {

}
