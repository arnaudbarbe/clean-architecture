package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.team.TeamService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;

@Service
public class MessageUpdateTeamHandler implements GenericHandler<Message<TeamDto>>{

	private TeamService teamService;

	@Autowired
	public MessageUpdateTeamHandler(final TeamService teamService) {
		super();
		this.teamService = teamService;
	}

	@Override
	public Object handle(final Message<TeamDto> payload, final MessageHeaders headers) {
		this.teamService.updateTeam(payload.getPayload().id(), payload.getPayload().toEntity());
		return null;
	}
}
