package fr.arnaud.cleanarchitecture.infrastructure.client.publisher.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@MessagingGateway
public interface LeaguePublisherService {

    @Gateway(requestChannel = "createLeagueV1AsyncOutboundChannel")
    void createLeagueAsync(LeagueDto league);
    
    @Gateway(requestChannel = "updateLeagueV1AsyncOutboundChannel")
    void updateLeagueAsync(LeagueDto league);

    @Gateway(requestChannel = "deleteLeagueV1AsyncOutboundChannel")
    void deleteLeagueAsync(UUID id);
}
