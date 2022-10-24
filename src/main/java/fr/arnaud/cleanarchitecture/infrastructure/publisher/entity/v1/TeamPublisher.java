package fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;

@MessagingGateway
public interface TeamPublisher {

    @Gateway(requestChannel = "createTeamV1OutboundChannel")
    void createTeamAsync(TeamDto team);
    
    @Gateway(requestChannel = "updateTeamV1OutboundChannel")
    void updateTeamAsync(TeamDto team);

    @Gateway(requestChannel = "deleteTeamV1OutboundChannel")
    void deleteTeamAsync(UUID id);
}
