package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;

@MessagingGateway
public interface PlayerEventPublisher {

    @Gateway(requestChannel = "createPlayerEventV1OutboundChannel")
    void createPlayerEvent(Event<PlayerDto> event);
        
    @Gateway(requestChannel = "updatePlayerEventV1OutboundChannel")
    void updatePlayerEvent(Event<PlayerDto> event);
        
    @Gateway(requestChannel = "deletePlayerEventV1OutboundChannel")
    void deletePlayerEvent(Event<UUID> event);
        
}
