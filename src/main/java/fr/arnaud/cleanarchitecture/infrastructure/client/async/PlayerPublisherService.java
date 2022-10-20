package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;

@MessagingGateway
public interface PlayerPublisherService {

    @Gateway(requestChannel = "createPlayerAsyncOutboundChannel")
    void createPlayerAsync(PlayerDto player);
        
    @Gateway(requestChannel = "updatePlayerAsyncOutboundChannel")
    void updatePlayerAsync(PlayerDto player);
        
    @Gateway(requestChannel = "deletePlayerAsyncOutboundChannel")
    void deletePlayerAsync(UUID id);
        
}
