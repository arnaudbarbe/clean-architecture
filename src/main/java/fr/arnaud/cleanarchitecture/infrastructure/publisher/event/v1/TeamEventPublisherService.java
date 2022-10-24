package fr.arnaud.cleanarchitecture.infrastructure.publisher.event.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;

@MessagingGateway
public interface TeamEventPublisherService {

    @Gateway(requestChannel = "createTeamEventV1OutboundChannel")
    void createTeamEvent(TeamDto team);
    
    @Gateway(requestChannel = "updateTeamEventV1OutboundChannel")
    void updateTeamEvent(TeamDto team);

    @Gateway(requestChannel = "deleteTeamEventV1OutboundChannel")
    void deleteTeamEvent(UUID id);
}
