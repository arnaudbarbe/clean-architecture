package fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@MessagingGateway
public interface LeaguePublisher {

    @Gateway(requestChannel = "createLeagueV1OutboundChannel")
    void createLeagueAsync(LeagueDto league);
    
    @Gateway(requestChannel = "updateLeagueV1OutboundChannel")
    void updateLeagueAsync(LeagueDto league);

    @Gateway(requestChannel = "deleteLeagueV1OutboundChannel")
    void deleteLeagueAsync(UUID id);
}