package fr.arnaud.cleanarchitecture.infrastructure.client.publisher.v1;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@MessagingGateway
public interface ChampionshipPublisherService {

    @Gateway(requestChannel = "createChampionshipV1AsyncOutboundChannel")
    void createChampionshipAsync(ChampionshipDto championship);
    
    @Gateway(requestChannel = "updateChampionshipV1AsyncOutboundChannel")
    void updateChampionshipAsync(ChampionshipDto championship);

    @Gateway(requestChannel = "deleteChampionshipV1AsyncOutboundChannel")
    void deleteChampionshipAsync(UUID id);
}
