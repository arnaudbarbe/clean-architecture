package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.v1.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;

@MessagingGateway
public interface SeasonPublisher {

    @Gateway(requestChannel = "createSeasonV1OutboundChannel")
    void createSeason(SeasonDto seasonDto);

    @Gateway(requestChannel = "updateSeasonV1OutboundChannel")
    void updateSeason(SeasonDto seasonDto);

    @Gateway(requestChannel = "deleteSeasonV1OutboundChannel")
    void deleteLeague(UUID id);
        
}
