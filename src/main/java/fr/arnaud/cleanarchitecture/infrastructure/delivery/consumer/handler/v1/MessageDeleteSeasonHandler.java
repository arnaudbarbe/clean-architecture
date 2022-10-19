package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.season.SeasonService;

@Service
public class MessageDeleteSeasonHandler implements GenericHandler<UUID>{

	private SeasonService seasonService;

	@Autowired
	public MessageDeleteSeasonHandler(final SeasonService seasonService) {
		super();
		this.seasonService = seasonService;
	}

	@Override
	public Object handle(final UUID id, final MessageHeaders headers) {
		this.seasonService.deleteSeason(id);
		return null;
	}
      
    

}
