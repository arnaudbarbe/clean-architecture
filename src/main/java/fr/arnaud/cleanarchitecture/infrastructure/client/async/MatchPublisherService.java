package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;

@MessagingGateway
public interface MatchPublisherService {

    @Gateway(requestChannel = "createMatchAsyncOutboundChannel")
    void createMatchAsync(MatchDto match);
    
    @Gateway(requestChannel = "updateMatchAsyncOutboundChannel")
    void updateMatchAsync(MatchDto match);

    @Gateway(requestChannel = "deleteMatchAsyncOutboundChannel")
    void deleteMatchAsync(UUID id);
}
