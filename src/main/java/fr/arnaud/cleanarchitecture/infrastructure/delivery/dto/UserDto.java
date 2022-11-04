package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto;

import javax.validation.constraints.NotNull;

public record UserDto(@NotNull String username, @NotNull String password) {

}
