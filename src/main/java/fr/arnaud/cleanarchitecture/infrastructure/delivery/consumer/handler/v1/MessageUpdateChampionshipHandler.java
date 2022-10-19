package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@Service
public class MessageUpdateChampionshipHandler implements GenericHandler<Message<ChampionshipDto>>{

	private ChampionshipService championshipService;

	@Autowired
	public MessageUpdateChampionshipHandler(final ChampionshipService championshipService) {
		super();
		this.championshipService = championshipService;
	}

	@Override
	public Object handle(final Message<ChampionshipDto> payload, final MessageHeaders headers) {
		this.championshipService.updateChampionship(payload.getPayload().id(), payload.getPayload().toEntity());
		return null;
	}
}
