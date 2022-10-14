package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;

@Service
public class MessageDeleteChampionshipHandler implements GenericHandler<Message<UUID>>{

	private ChampionshipService championshipService;

	@Autowired
	public MessageDeleteChampionshipHandler(final ChampionshipService championshipService) {
		super();
		this.championshipService = championshipService;
	}

	@Override
	public Object handle(final Message<UUID> payload, final MessageHeaders headers) {
		this.championshipService.deleteChampionship(payload.getPayload());
		return null;
	}
      
    

}
