package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.v1.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;

@MessagingGateway
public interface PlayerPublisher {

    @Gateway(requestChannel = "createPlayerV1OutboundChannel")
    void createPlayer(PlayerDto player);
        
    @Gateway(requestChannel = "updatePlayerV1OutboundChannel")
    void updatePlayer(PlayerDto player);
        
    @Gateway(requestChannel = "deletePlayerV1OutboundChannel")
    void deletePlayer(UUID id);
        
}
