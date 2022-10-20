package fr.arnaud.cleanarchitecture.infrastructure.client.async;

import java.util.UUID;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@MessagingGateway
public interface ChampionshipPublisherService {

    @Gateway(requestChannel = "createChampionshipAsyncOutboundChannel")
    void createChampionshipAsync(ChampionshipDto championship);
    
    @Gateway(requestChannel = "updateChampionshipAsyncOutboundChannel")
    void updateChampionshipAsync(ChampionshipDto championship);

    @Gateway(requestChannel = "deleteChampionshipAsyncOutboundChannel")
    void deleteChampionshipAsync(UUID id);
}
