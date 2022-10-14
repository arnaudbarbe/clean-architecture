package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.match.MatchService;

@Service
public class MessageDeleteMatchHandler implements GenericHandler<Message<UUID>>{

	private MatchService matchService;

	@Autowired
	public MessageDeleteMatchHandler(final MatchService matchService) {
		super();
		this.matchService = matchService;
	}

	@Override
	public Object handle(final Message<UUID> payload, final MessageHeaders headers) {
		this.matchService.deleteMatch(payload.getPayload());
		return null;
	}
      
    

}
