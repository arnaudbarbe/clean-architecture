package fr.arnaud.cleanarchitecture.infrastructure.publisher.event.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@MessagingGateway
public interface LeagueEventPublisherService {

    @Gateway(requestChannel = "createLeagueEventV1OutboundChannel")
    void createLeagueEvent(LeagueDto league);
    
    @Gateway(requestChannel = "updateLeagueEventV1OutboundChannel")
    void updateLeagueEvent(LeagueDto league);

    @Gateway(requestChannel = "deleteLeagueEventV1OutboundChannel")
    void deleteLeagueEvent(UUID id);
}
