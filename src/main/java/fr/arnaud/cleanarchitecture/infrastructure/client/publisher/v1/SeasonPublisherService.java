package fr.arnaud.cleanarchitecture.infrastructure.client.publisher.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;

@MessagingGateway
public interface SeasonPublisherService {

    @Gateway(requestChannel = "createSeasonV1AsyncOutboundChannel")
    void createSeasonAsync(SeasonDto seasonDto);

    @Gateway(requestChannel = "updateSeasonV1AsyncOutboundChannel")
    void updateSeasonAsync(SeasonDto seasonDto);

    @Gateway(requestChannel = "deleteSeasonV1AsyncOutboundChannel")
    void deleteLeagueAsync(UUID id);
        
}
