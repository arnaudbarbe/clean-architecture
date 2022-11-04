package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageDeleteChampionshipHandler implements GenericHandler<UUID>{

	private ChampionshipService championshipService;

	@Autowired
	public MessageDeleteChampionshipHandler(final ChampionshipService championshipService) {
		super();
		this.championshipService = championshipService;
	}

	@Override
	public Object handle(final UUID id, final MessageHeaders headers) {
		try {
			this.championshipService.deleteChampionship(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
