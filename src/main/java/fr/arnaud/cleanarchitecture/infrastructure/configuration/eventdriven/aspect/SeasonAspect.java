package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Season;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1.publisher.SeasonEventPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;


@Aspect
@Component
public class SeasonAspect {
    
	private final SeasonEventPublisher seasonEventPublisher;
	
	@Autowired
	public SeasonAspect(final SeasonEventPublisher seasonEventPublisher) {
		this.seasonEventPublisher = seasonEventPublisher;
	}

	@After("execution(* fr.arnaud.cleanarchitecture.core.service.season.DomainSeasonService.createSeason(..))  && args(season)")
    public void eventAfterCreateSeason(final JoinPoint joinPoint, final Season season) {

    	Event<SeasonDto> event = 
    			new Event<>(
    					Event.StandardStatus.CREATED.name(), 
    					SeasonDto.fromEntity(season));

    	this.seasonEventPublisher.createSeasonEvent(event);
    }

    @After("execution(* fr.arnaud.cleanarchitecture.core.service.season.DomainSeasonService.updateSeason(..)) && args(id, season)")
    public void eventAfterUpdateSeason(final JoinPoint joinPoint, final UUID id, final Season season) {

    	Event<SeasonDto> event = 
    			new Event<>(
    					Event.StandardStatus.UPDATED.name(), 
    					SeasonDto.fromEntity(season));

    	this.seasonEventPublisher.updateSeasonEvent(event);
    }

    @After("execution(* fr.arnaud.cleanarchitecture.core.service.season.DomainSeasonService.deleteSeason(..)) && args(id)")
    public void eventAfterCreateSeason(final JoinPoint joinPoint, final UUID id) {
    
    	Event<UUID> event = 
    			new Event<>(
    					Event.StandardStatus.DELETED.name(), 
    					id);

    	this.seasonEventPublisher.deleteSeasonEvent(event);
    }
}
