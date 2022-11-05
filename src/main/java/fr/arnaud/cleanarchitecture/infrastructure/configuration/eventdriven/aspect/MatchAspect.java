package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.publisher.v1.MatchEventPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;


@Aspect
@Component
public class MatchAspect {
    
	private final MatchEventPublisher matchEventPublisher;
	
	@Autowired
	public MatchAspect(final MatchEventPublisher matchEventPublisher) {
		this.matchEventPublisher = matchEventPublisher;
	}

	@After("execution(* fr.arnaud.cleanarchitecture.core.service.match.DomainMatchService.createMatch(..))  && args(match)")
    public void eventAfterCreateMatch(final JoinPoint joinPoint, final Match match) {

    	Event<MatchDto> event = 
    			new Event<>(
    					Event.StandardStatus.CREATED.name(), 
    					MatchDto.fromEntity(match));

    	this.matchEventPublisher.createMatchEvent(event);
    }

    @After("execution(* fr.arnaud.cleanarchitecture.core.service.match.DomainMatchService.updateMatch(..)) && args(id, match)")
    public void eventAfterUpdateMatch(final JoinPoint joinPoint, final UUID id, final Match match) {

    	Event<MatchDto> event = 
    			new Event<>(
    					Event.StandardStatus.UPDATED.name(), 
    					MatchDto.fromEntity(match));

    	this.matchEventPublisher.updateMatchEvent(event);
    }
	
    @After("execution(* fr.arnaud.cleanarchitecture.core.service.match.DomainMatchService.deleteMatch(..)) && args(id)")
    public void eventAfterCreateMatch(final JoinPoint joinPoint, final UUID id) {
    
    	Event<UUID> event = 
    			new Event<>(
    					Event.StandardStatus.DELETED.name(), 
    					id);

    	this.matchEventPublisher.deleteMatchEvent(event);
    }
}
