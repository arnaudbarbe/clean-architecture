package fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1;

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
