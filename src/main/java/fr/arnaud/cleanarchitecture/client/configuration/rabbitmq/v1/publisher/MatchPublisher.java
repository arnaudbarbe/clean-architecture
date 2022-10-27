package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.v1.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;

@MessagingGateway
public interface MatchPublisher {

    @Gateway(requestChannel = "createMatchV1OutboundChannel")
    void createMatch(MatchDto match);
    
    @Gateway(requestChannel = "updateMatchV1OutboundChannel")
    void updateMatch(MatchDto match);

    @Gateway(requestChannel = "deleteMatchV1OutboundChannel")
    void deleteMatch(UUID id);
}
