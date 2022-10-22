package fr.arnaud.cleanarchitecture.infrastructure.client.publisher.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;

@MessagingGateway
public interface TeamPublisherService {

    @Gateway(requestChannel = "createTeamV1AsyncOutboundChannel")
    void createTeamAsync(TeamDto team);
    
    @Gateway(requestChannel = "updateTeamV1AsyncOutboundChannel")
    void updateTeamAsync(TeamDto team);

    @Gateway(requestChannel = "deleteTeamV1AsyncOutboundChannel")
    void deleteTeamAsync(UUID id);
}
