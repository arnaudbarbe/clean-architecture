package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.league.LeagueService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;

@Service
public class MessageUpdateLeagueHandler implements GenericHandler<LeagueDto>{

	private LeagueService leagueService;

	@Autowired
	public MessageUpdateLeagueHandler(final LeagueService leagueService) {
		super();
		this.leagueService = leagueService;
	}

	@Override
	public Object handle(final LeagueDto league, final MessageHeaders headers) {
		this.leagueService.updateLeague(league.id(), league.toEntity());
		return null;
	}
}
