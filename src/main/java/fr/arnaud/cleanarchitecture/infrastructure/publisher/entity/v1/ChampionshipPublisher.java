package fr.arnaud.cleanarchitecture.infrastructure.publisher.entity.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@MessagingGateway
public interface ChampionshipPublisher {

    @Gateway(requestChannel = "createChampionshipV1OutboundChannel")
    void createChampionship(ChampionshipDto championship);
    
    @Gateway(requestChannel = "updateChampionshipV1OutboundChannel")
    void updateChampionship(ChampionshipDto championship);

    @Gateway(requestChannel = "deleteChampionshipV1OutboundChannel")
    void deleteChampionship(UUID id);
}
