package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.match.MatchService;

@Service
public class MessageDeleteMatchHandler implements GenericHandler<UUID>{

	private MatchService matchService;

	@Autowired
	public MessageDeleteMatchHandler(final MatchService matchService) {
		super();
		this.matchService = matchService;
	}

	@Override
	public Object handle(final UUID id, final MessageHeaders headers) {
		this.matchService.deleteMatch(id);
		return null;
	}
      
    

}
