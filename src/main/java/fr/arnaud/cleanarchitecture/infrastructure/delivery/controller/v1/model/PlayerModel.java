package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString 
@EqualsAndHashCode(callSuper = true, of= {"id", "firstName", "lastName"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerModel extends RepresentationModel<PlayerModel> implements Model {

	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id;
	@NotNull String firstName;
	@NotNull String lastName;

	public fr.arnaud.cleanarchitecture.core.model.Player toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.Player.builder()
				.id(this.id)
				.firstName(this.firstName)
				.lastName(this.lastName).build();
	}
	
	public static PlayerModel fromEntity(final fr.arnaud.cleanarchitecture.core.model.Player player) {
		if (player == null) {
			return null;
		} else {
			return new PlayerModel(
					player.getId(), 
					player.getFirstName(), 
					player.getLastName());
		}
	}
}
