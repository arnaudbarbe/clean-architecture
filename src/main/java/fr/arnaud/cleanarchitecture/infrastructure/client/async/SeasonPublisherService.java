package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;

@MessagingGateway
public interface SeasonPublisherService {

    @Gateway(requestChannel = "createSeasonAsyncOutboundChannel")
    void createSeasonAsync(SeasonDto seasonDto);

    @Gateway(requestChannel = "updateSeasonAsyncOutboundChannel")
    void updateSeasonAsync(SeasonDto seasonDto);

    @Gateway(requestChannel = "deleteSeasonAsyncOutboundChannel")
    void deleteLeagueAsync(UUID id);
        
}
