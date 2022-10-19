package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;

@MessagingGateway
public interface PlayerPublisherService {

    
    /**
     * The player to create
     */
    @Gateway(requestChannel = "createPlayerAsyncOutboundChannel")
    void createPlayerAsync(PlayerDto player);
        
    
        
    /**
     * The player to update
     */
    @Gateway(requestChannel = "updatePlayerAsyncOutboundChannel")
    void updatePlayerAsync(PlayerDto player);
        
    
        
    /**
     * The player to delete
     */
    @Gateway(requestChannel = "deletePlayerAsyncOutboundChannel")
    void deletePlayerAsync(UUID id);
        
    
}
