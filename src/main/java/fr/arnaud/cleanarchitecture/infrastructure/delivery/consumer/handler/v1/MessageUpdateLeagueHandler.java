package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.league.LeagueService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@Service
public class MessageUpdateLeagueHandler implements GenericHandler<Message<LeagueDto>>{

	private LeagueService leagueService;

	@Autowired
	public MessageUpdateLeagueHandler(final LeagueService leagueService) {
		super();
		this.leagueService = leagueService;
	}

	@Override
	public Object handle(final Message<LeagueDto> payload, final MessageHeaders headers) {
		this.leagueService.updateLeague(payload.getPayload().id(), payload.getPayload().toEntity());
		return null;
	}
}
