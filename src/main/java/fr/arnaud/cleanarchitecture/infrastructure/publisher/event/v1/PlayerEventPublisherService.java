package fr.arnaud.cleanarchitecture.infrastructure.publisher.event.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;

@MessagingGateway
public interface PlayerEventPublisherService {

    @Gateway(requestChannel = "createPlayerEventV1OutboundChannel")
    void createPlayerEvent(PlayerDto player);
        
    @Gateway(requestChannel = "updatePlayerEventV1OutboundChannel")
    void updatePlayerEvent(PlayerDto player);
        
    @Gateway(requestChannel = "deletePlayerEventV1OutboundChannel")
    void deletePlayerEvent(UUID id);
        
}
