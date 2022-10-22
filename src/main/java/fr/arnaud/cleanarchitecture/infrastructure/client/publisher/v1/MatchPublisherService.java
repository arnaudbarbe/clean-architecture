package fr.arnaud.cleanarchitecture.infrastructure.client.publisher.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;

@MessagingGateway
public interface MatchPublisherService {

    @Gateway(requestChannel = "createMatchV1AsyncOutboundChannel")
    void createMatchAsync(MatchDto match);
    
    @Gateway(requestChannel = "updateMatchV1AsyncOutboundChannel")
    void updateMatchAsync(MatchDto match);

    @Gateway(requestChannel = "deleteMatchV1AsyncOutboundChannel")
    void deleteMatchAsync(UUID id);
}
