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
     * The league to delete
     */
    @Gateway(requestChannel = "deleteLeagueSubOutboundChannel")
    void deleteLeagueSub(String data);
        
    
}
