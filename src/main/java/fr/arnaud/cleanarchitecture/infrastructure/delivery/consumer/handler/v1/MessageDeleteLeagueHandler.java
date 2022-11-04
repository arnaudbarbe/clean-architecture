package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.league.LeagueService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		try {
			this.leagueService.deleteLeague(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
