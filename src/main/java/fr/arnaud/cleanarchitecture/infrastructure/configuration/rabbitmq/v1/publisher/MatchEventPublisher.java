package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;

@MessagingGateway
public interface MatchEventPublisher {

    @Gateway(requestChannel = "createMatchEventV1OutboundChannel")
    void createMatchEvent(Event<MatchDto> event);
    
    @Gateway(requestChannel = "updateMatchEventV1OutboundChannel")
    void updateMatchEvent(Event<MatchDto> event);

    @Gateway(requestChannel = "deleteMatchEventV1OutboundChannel")
    void deleteMatchEvent(Event<UUID> event);
}
