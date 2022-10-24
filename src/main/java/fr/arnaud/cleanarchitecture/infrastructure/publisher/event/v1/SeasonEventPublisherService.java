package fr.arnaud.cleanarchitecture.infrastructure.publisher.event.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;

@MessagingGateway
public interface SeasonEventPublisherService {

    @Gateway(requestChannel = "createSeasonEventV1OutboundChannel")
    void createSeasonEvent(SeasonDto seasonDto);

    @Gateway(requestChannel = "updateSeasonEventV1OutboundChannel")
    void updateSeasonEvent(SeasonDto seasonDto);

    @Gateway(requestChannel = "deleteSeasonEventV1OutboundChannel")
    void deleteLeagueEvent(UUID id);
        
}
