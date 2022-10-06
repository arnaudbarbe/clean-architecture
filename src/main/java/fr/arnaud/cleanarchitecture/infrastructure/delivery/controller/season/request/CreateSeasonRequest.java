package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.request;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Season;
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
@EqualsAndHashCode(of= {"season"})
@ToString(of= {"season"})
@Builder
public class CreateSeasonRequest {
    @NotNull 
    Season season;
}