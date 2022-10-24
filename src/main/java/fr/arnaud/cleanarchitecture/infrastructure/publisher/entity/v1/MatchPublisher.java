package fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;

@MessagingGateway
public interface MatchPublisher {

    @Gateway(requestChannel = "createMatchV1OutboundChannel")
    void createMatchAsync(MatchDto match);
    
    @Gateway(requestChannel = "updateMatchV1OutboundChannel")
    void updateMatchAsync(MatchDto match);

    @Gateway(requestChannel = "deleteMatchV1OutboundChannel")
    void deleteMatchAsync(UUID id);
}
