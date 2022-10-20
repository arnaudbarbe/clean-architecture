package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;

@MessagingGateway
public interface TeamPublisherService {

    @Gateway(requestChannel = "createTeamAsyncOutboundChannel")
    void createTeamAsync(TeamDto team);
    
    @Gateway(requestChannel = "updateTeamAsyncOutboundChannel")
    void updateTeamAsync(TeamDto team);

    @Gateway(requestChannel = "deleteTeamAsyncOutboundChannel")
    void deleteTeamAsync(UUID id);
}
