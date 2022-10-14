package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.match.MatchService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;

@Service
public class MessageCreateMatchHandler implements GenericHandler<Message<MatchDto>>{

	private MatchService matchService;
	
	@Autowired
	public MessageCreateMatchHandler(final MatchService matchService) {
		super();
		this.matchService = matchService;
	}

	@Override
	public Object handle(final Message<MatchDto> payload, final MessageHeaders headers) {
		return this.matchService.createMatch(payload.getPayload().toEntity());
	}
}
