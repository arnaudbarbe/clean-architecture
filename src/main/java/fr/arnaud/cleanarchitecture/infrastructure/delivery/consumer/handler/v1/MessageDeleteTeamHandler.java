package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.team.TeamService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageDeleteTeamHandler implements GenericHandler<UUID>{

	private TeamService teamService;

	@Autowired
	public MessageDeleteTeamHandler(final TeamService teamService) {
		super();
		this.teamService = teamService;
	}

	@Override
	public Object handle(final UUID id, final MessageHeaders headers) {
		try {
			this.teamService.deleteTeam(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
