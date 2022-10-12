package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.player.PlayerService;


@Service
public class MessageDeletePlayerHandler implements GenericHandler<Message<UUID>>{

 
	private PlayerService playerService;
	
	@Autowired
	public MessageDeletePlayerHandler(final PlayerService playerService) {
		super();
		this.playerService = playerService;
	}

	@Override
	public Object handle(final Message<UUID> payload, final MessageHeaders headers) {
		this.playerService.deletePlayer(payload.getPayload());
		return null;
	}
}
