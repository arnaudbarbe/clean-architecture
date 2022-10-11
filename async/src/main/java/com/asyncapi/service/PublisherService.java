package com.asyncapi.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface PublisherService {

    
        
    /**
     * The league to create
     */
    @Gateway(requestChannel = "createLeagueSubOutboundChannel")
    void createLeagueSub(String data);
        
    
        
    /**
     * The league to update
     */
    @Gateway(requestChannel = "updateLeagueSubOutboundChannel")
    void updateLeagueSub(String data);
        
    
        
    /**
     * The league to delete
     */
    @Gateway(requestChannel = "deleteLeagueSubOutboundChannel")
    void deleteLeagueSub(String data);
        
    
        
    /**
     * The player to create
     */
    @Gateway(requestChannel = "createPlayerSubOutboundChannel")
    void createPlayerSub(String data);
        
    
        
    /**
     * The player to update
     */
    @Gateway(requestChannel = "updatePlayerSubOutboundChannel")
    void updatePlayerSub(String data);
        
    
        
    /**
     * The player to delete
     */
    @Gateway(requestChannel = "deletePlayerSubOutboundChannel")
    void deletePlayerSub(String data);
        
    
}
