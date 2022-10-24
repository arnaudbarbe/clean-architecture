package fr.arnaud.cleanarchitecture.infrastructure.publisher.event.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;

@MessagingGateway
public interface MatchEventPublisherService {

    @Gateway(requestChannel = "createMatchEventV1OutboundChannel")
    void createMatchEvent(MatchDto match);
    
    @Gateway(requestChannel = "updateMatchEventV1OutboundChannel")
    void updateMatchEvent(MatchDto match);

    @Gateway(requestChannel = "deleteMatchEventV1OutboundChannel")
    void deleteMatchEvent(UUID id);
}
