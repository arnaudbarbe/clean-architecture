package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.team.TeamService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageCreateTeamHandler implements GenericHandler<TeamDto>{

	private TeamService teamService;
	
	@Autowired
	public MessageCreateTeamHandler(final TeamService teamService) {
		super();
		this.teamService = teamService;
	}

	@Override
	public Object handle(final TeamDto team, final MessageHeaders headers) {
		try {
			this.teamService.createTeam(team.toEntity());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
