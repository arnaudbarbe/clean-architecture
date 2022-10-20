package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@MessagingGateway
public interface LeaguePublisherService {

    @Gateway(requestChannel = "createLeagueAsyncOutboundChannel")
    void createLeagueAsync(LeagueDto league);
    
    @Gateway(requestChannel = "updateLeagueAsyncOutboundChannel")
    void updateLeagueAsync(LeagueDto league);

    @Gateway(requestChannel = "deleteLeagueAsyncOutboundChannel")
    void deleteLeagueAsync(UUID id);
}
