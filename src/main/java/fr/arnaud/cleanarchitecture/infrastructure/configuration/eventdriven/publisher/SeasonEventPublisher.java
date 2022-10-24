package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;

@MessagingGateway
public interface SeasonEventPublisher {

    @Gateway(requestChannel = "createSeasonEventV1OutboundChannel")
    void createSeasonEvent(Event<SeasonDto> event);

    @Gateway(requestChannel = "updateSeasonEventV1OutboundChannel")
    void updateSeasonEvent(Event<SeasonDto> event);

    @Gateway(requestChannel = "deleteSeasonEventV1OutboundChannel")
    void deleteSeasonEvent(Event<UUID> event);
        
}
