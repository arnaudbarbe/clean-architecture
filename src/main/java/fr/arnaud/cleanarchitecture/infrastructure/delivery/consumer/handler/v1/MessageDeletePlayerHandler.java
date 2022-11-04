package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.player.PlayerService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MessageDeletePlayerHandler implements GenericHandler<UUID>{

 
	private PlayerService playerService;
	
	@Autowired
	public MessageDeletePlayerHandler(final PlayerService playerService) {
		super();
		this.playerService = playerService;
	}

	@Override
	public Object handle(final UUID id, final MessageHeaders headers) {
		try {
			this.playerService.deletePlayer(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
