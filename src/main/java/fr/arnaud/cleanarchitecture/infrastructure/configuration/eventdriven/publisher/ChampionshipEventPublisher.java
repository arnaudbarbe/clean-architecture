package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@MessagingGateway
public interface ChampionshipEventPublisher {

    @Gateway(requestChannel = "createChampionshipEventV1OutboundChannel")
    void createChampionshipEvent(Event<ChampionshipDto> event);
    
    @Gateway(requestChannel = "updateChampionshipEventV1OutboundChannel")
    void updateChampionshipEvent(Event<ChampionshipDto> event);

    @Gateway(requestChannel = "deleteChampionshipEventV1OutboundChannel")
    void deleteChampionshipEvent(Event<UUID> event);
}
