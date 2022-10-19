package fr.arnaud.cleanarchitecture.infrastructure.delivery.consumer.handler.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import fr.arnaud.cleanarchitecture.core.service.season.SeasonService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;

@Service
public class MessageUpdateSeasonHandler implements GenericHandler<SeasonDto>{

	private SeasonService seasonService;

	@Autowired
	public MessageUpdateSeasonHandler(final SeasonService seasonService) {
		super();
		this.seasonService = seasonService;
	}

	@Override
	public Object handle(final SeasonDto season, final MessageHeaders headers) {
		this.seasonService.updateSeason(season.id(), season.toEntity());
		return null;
	}
}
