package fr.arnaud.cleanarchitecture.infrastructure.publisher.event.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@MessagingGateway
public interface ChampionshipEventPublisherService {

    @Gateway(requestChannel = "createChampionshipEventV1OutboundChannel")
    void createChampionshipEvent(ChampionshipDto championship);
    
    @Gateway(requestChannel = "updateChampionshipEventV1OutboundChannel")
    void updateChampionshipEvent(ChampionshipDto championship);

    @Gateway(requestChannel = "deleteChampionshipEventV1OutboundChannel")
    void deleteChampionshipEvent(UUID id);
}
