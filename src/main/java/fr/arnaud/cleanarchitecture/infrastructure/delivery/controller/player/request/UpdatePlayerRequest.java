package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.request;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Player;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"player"})
public class UpdatePlayerRequest {
    @NotNull 
    Player player;

}
