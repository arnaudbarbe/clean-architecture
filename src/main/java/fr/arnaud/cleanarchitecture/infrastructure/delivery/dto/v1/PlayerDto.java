package fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1;

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
public class PlayerDto extends RepresentationModel<PlayerDto> implements Dto {

	private static final long serialVersionUID = 1L;
	
	@NotNull UUID id;
	@NotNull String firstName;
	@NotNull String lastName;

	public fr.arnaud.cleanarchitecture.core.entity.Player toEntity() {
		return fr.arnaud.cleanarchitecture.core.entity.Player.builder()
				.id(this.id)
				.firstName(this.firstName)
				.lastName(this.lastName).build();
	}
	
	public static PlayerDto fromEntity(final fr.arnaud.cleanarchitecture.core.entity.Player player) {
		if (player == null) {
			return null;
		} else {
			return new PlayerDto(
					player.getId(), 
					player.getFirstName(), 
					player.getLastName());
		}
	}
}
