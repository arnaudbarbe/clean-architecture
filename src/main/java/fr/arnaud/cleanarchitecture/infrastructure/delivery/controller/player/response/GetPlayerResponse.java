package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.response;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Player;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of= {"player"})
@ToString(of= {"player"})
@Builder
public class GetPlayerResponse {
	@NotNull 
	Player player;
}
