package fr.arnaud.cleanarchitecture.client.configuration.rabbitmq.v1.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;

@MessagingGateway
public interface TeamPublisher {

    @Gateway(requestChannel = "createTeamV1OutboundChannel")
    void createTeam(TeamDto team);
    
    @Gateway(requestChannel = "updateTeamV1OutboundChannel")
    void updateTeam(TeamDto team);

    @Gateway(requestChannel = "deleteTeamV1OutboundChannel")
    void deleteTeam(UUID id);
}
