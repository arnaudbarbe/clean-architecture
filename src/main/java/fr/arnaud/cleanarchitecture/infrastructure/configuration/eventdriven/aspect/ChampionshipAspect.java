package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.model.Championship;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.v1.publisher.ChampionshipEventPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;

@Aspect
@Component
public class ChampionshipAspect {
	
	private final ChampionshipEventPublisher championshipEventPublisher;
	
	@Autowired
	public ChampionshipAspect(final ChampionshipEventPublisher championshipEventPublisher) {
		this.championshipEventPublisher = championshipEventPublisher;
	}
	
	@After("execution(* fr.arnaud.cleanarchitecture.core.service.championship.DomainChampionshipService.createChampionship(..)) && args(championship)")
    public void eventAfterCreateChampionship(final JoinPoint joinPoint, final Championship championship) {

    	Event<ChampionshipDto> event = 
    			new Event<>(
    					Event.StandardStatus.CREATED.name(), 
    					ChampionshipDto.fromEntity(championship));
    	
    	this.championshipEventPublisher.createChampionshipEvent(event);
    }

	@After("execution(* fr.arnaud.cleanarchitecture.core.service.championship.DomainChampionshipService.updateChampionship(..)) && args(id, championship)")
    public void eventAfterUpdateChampionship(final JoinPoint joinPoint, final UUID id, final Championship championship) {

    	Event<ChampionshipDto> event = 
    			new Event<>(
    					Event.StandardStatus.UPDATED.name(), 
    					ChampionshipDto.fromEntity(championship));
    	
    	this.championshipEventPublisher.updateChampionshipEvent(event);
    }

	@After("execution(* fr.arnaud.cleanarchitecture.core.service.championship.DomainChampionshipService.deleteChampionship(..)) && args(id)")
    public void eventAfterdeleteChampionship(final JoinPoint joinPoint, final UUID id) {

    	Event<UUID> event = 
    			new Event<>(
    					Event.StandardStatus.DELETED.name(), id);
    	
    	this.championshipEventPublisher.deleteChampionshipEvent(event);
    }
}
