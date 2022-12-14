package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.match.MatchService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageCreateMatchHandler implements GenericHandler<MatchDto>{

	private MatchService matchService;
	
	@Autowired
	public MessageCreateMatchHandler(final MatchService matchService) {
		super();
		this.matchService = matchService;
	}

	@Override
	public Object handle(final MatchDto match, final MessageHeaders headers) {
		try {
			this.matchService.createMatch(match.toEntity());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
