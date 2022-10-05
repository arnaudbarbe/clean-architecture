package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.request;

import javax.validation.constraints.NotNull;

import fr.arnaud.cleanarchitecture.core.model.Championship;
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
@EqualsAndHashCode(of= {"championship"})
@ToString(of= {"championship"})
@Builder
public class CreateChampionshipRequest {
    @NotNull 
    Championship championship;
}