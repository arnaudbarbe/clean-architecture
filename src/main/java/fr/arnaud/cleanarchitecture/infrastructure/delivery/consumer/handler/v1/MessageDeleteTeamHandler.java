package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.team.TeamService;

@Service
public class MessageDeleteTeamHandler implements GenericHandler<Message<UUID>>{

	private TeamService teamService;

	@Autowired
	public MessageDeleteTeamHandler(final TeamService teamService) {
		super();
		this.teamService = teamService;
	}

	@Override
	public Object handle(final Message<UUID> payload, final MessageHeaders headers) {
		this.teamService.deleteTeam(payload.getPayload());
		return null;
	}
      
    

}
