package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.league.LeagueService;

@Service
public class MessageDeleteLeagueHandler implements GenericHandler<UUID>{

	private LeagueService leagueService;

	@Autowired
	public MessageDeleteLeagueHandler(final LeagueService leagueService) {
		super();
		this.leagueService = leagueService;
	}

	@Override
	public Object handle(final UUID id, final MessageHeaders headers) {
		this.leagueService.deleteLeague(id);
		return null;
	}
}
