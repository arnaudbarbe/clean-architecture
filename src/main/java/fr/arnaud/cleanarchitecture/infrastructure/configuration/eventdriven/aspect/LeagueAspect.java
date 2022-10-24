package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import fr.arnaud.cleanarchitecture.core.entity.League;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.publisher.LeagueEventPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;


@Aspect
@Configuration
public class LeagueAspect {
    
	private final LeagueEventPublisher leagueEventPublisher;
	
	@Autowired
	public LeagueAspect(final LeagueEventPublisher leagueEventPublisher) {
		this.leagueEventPublisher = leagueEventPublisher;
	}
	
    @After("execution(* fr.arnaud.cleanarchitecture.core.service.league.DomainLeagueService.createLeague(..)) && args(league)")
    public void eventAfterCreateLeague(final JoinPoint joinPoint, final League league) {

    	Event<LeagueDto> event = 
    			new Event<>(
    					Event.StandardStatus.CREATED.name(), 
    					LeagueDto.fromEntity(league));

    	this.leagueEventPublisher.createLeagueEvent(event);
    }
	
    @After("execution(* fr.arnaud.cleanarchitecture.core.service.league.DomainLeagueService.updateLeague(..)) && args(league)")
    public void eventAfterUpdateLeague(final JoinPoint joinPoint, final League league) {

    	Event<LeagueDto> event = 
    			new Event<>(
    					Event.StandardStatus.UPDATED.name(), 
    					LeagueDto.fromEntity(league));

    	this.leagueEventPublisher.updateLeagueEvent(event);
    }
	
    @After("execution(* fr.arnaud.cleanarchitecture.core.service.league.DomainLeagueService.deleteLeague(..)) && args(id)")
    public void eventAfterCreateLeague(final JoinPoint joinPoint, final UUID id) {
    
    	Event<UUID> event = 
    			new Event<>(
    					Event.StandardStatus.DELETED.name(), 
    					id);

    	this.leagueEventPublisher.deleteLeagueEvent(event);
    }
}
