package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@MessagingGateway
public interface LeaguePublisherService {

    
        
    /**
     * The league to create
     */
    @Gateway(requestChannel = "createLeagueAsyncOutboundChannel")
    void createLeagueAsync(LeagueDto league);
        
    
        
    /**
     * The league to update
     */
    @Gateway(requestChannel = "updateLeagueAsyncOutboundChannel")
    void updateLeagueAsync(LeagueDto league);
        
    
        
    /**
     * The league to delete
     */
    @Gateway(requestChannel = "deleteLeagueAsyncOutboundChannel")
    void deleteLeagueAsync(UUID id);
        
}
