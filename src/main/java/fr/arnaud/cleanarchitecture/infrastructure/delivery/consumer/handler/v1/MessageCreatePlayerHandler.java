package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.player.PlayerService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;

@Service
public class MessageCreatePlayerHandler implements GenericHandler<PlayerDto>{


	private PlayerService playerService;
	
	@Autowired
	public MessageCreatePlayerHandler(final PlayerService playerService) {
		super();
		this.playerService = playerService;
	}

	@Override
	public Object handle(final PlayerDto player, final MessageHeaders headers) {
		this.playerService.createPlayer(player.toEntity());
		return null;
	}
}
