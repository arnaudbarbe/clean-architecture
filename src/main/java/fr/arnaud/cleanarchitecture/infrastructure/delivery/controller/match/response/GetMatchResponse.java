package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.response;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Match;
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
@EqualsAndHashCode(of= {"match"})
@ToString(of= {"match"})
@Builder
public class GetMatchResponse {
	@NotNull 
	Match match;
}
