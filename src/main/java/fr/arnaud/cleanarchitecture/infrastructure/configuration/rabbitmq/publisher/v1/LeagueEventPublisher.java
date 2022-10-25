package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.publisher.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@MessagingGateway
public interface LeagueEventPublisher {

    @Gateway(requestChannel = "createLeagueEventV1OutboundChannel")
    void createLeagueEvent(Event<LeagueDto> event);
    
    @Gateway(requestChannel = "updateLeagueEventV1OutboundChannel")
    void updateLeagueEvent(Event<LeagueDto> event);

    @Gateway(requestChannel = "deleteLeagueEventV1OutboundChannel")
    void deleteLeagueEvent(Event<UUID> event);
}
