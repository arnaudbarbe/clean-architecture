package fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;

@MessagingGateway
public interface SeasonPublisher {

    @Gateway(requestChannel = "createSeasonV1OutboundChannel")
    void createSeasonAsync(SeasonDto seasonDto);

    @Gateway(requestChannel = "updateSeasonV1OutboundChannel")
    void updateSeasonAsync(SeasonDto seasonDto);

    @Gateway(requestChannel = "deleteSeasonV1OutboundChannel")
    void deleteLeagueAsync(UUID id);
        
}
