package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@Service
public class MessageCreateChampionshipHandler implements GenericHandler<Message<ChampionshipDto>>{

	private ChampionshipService championshipService;
	
	@Autowired
	public MessageCreateChampionshipHandler(final ChampionshipService championshipService) {
		super();
		this.championshipService = championshipService;
	}

	@Override
	public Object handle(final Message<ChampionshipDto> payload, final MessageHeaders headers) {
		return this.championshipService.createChampionship(payload.getPayload().toEntity());
	}
}
