package fr.arnaud.cleanarchitecture.infrastructure.client.publisher.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;

@MessagingGateway
public interface PlayerPublisherService {

    @Gateway(requestChannel = "createPlayerV1AsyncOutboundChannel")
    void createPlayerAsync(PlayerDto player);
        
    @Gateway(requestChannel = "updatePlayerV1AsyncOutboundChannel")
    void updatePlayerAsync(PlayerDto player);
        
    @Gateway(requestChannel = "deletePlayerV1AsyncOutboundChannel")
    void deletePlayerAsync(UUID id);
        
}
