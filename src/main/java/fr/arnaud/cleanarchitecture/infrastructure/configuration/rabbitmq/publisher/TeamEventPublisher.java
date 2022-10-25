package fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.publisher;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;

@MessagingGateway
public interface TeamEventPublisher {

    @Gateway(requestChannel = "createTeamEventV1OutboundChannel")
    void createTeamEvent(Event<TeamDto> event);
    
    @Gateway(requestChannel = "updateTeamEventV1OutboundChannel")
    void updateTeamEvent(Event<TeamDto> event);

    @Gateway(requestChannel = "deleteTeamEventV1OutboundChannel")
    void deleteTeamEvent(Event<UUID> event);
}
