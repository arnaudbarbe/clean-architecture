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
@EqualsAndHashCode(callSuper = true, of= {"id", "name"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeagueModel extends RepresentationModel<LeagueModel> implements Model {

	private static final long serialVersionUID = 1L;

	@NotNull UUID id;
	@NotNull String name;

	public fr.arnaud.cleanarchitecture.core.model.League toEntity() {
		return fr.arnaud.cleanarchitecture.core.model.League.builder()
				.id(this.id)
				.name(this.name).build();
	}
	
	public static LeagueModel fromEntity(final fr.arnaud.cleanarchitecture.core.model.League league) {
		if (league == null) {
			return null;
		} else {
			return new LeagueModel(
					league.getId(), 
					league.getName());
		}
	}
}
