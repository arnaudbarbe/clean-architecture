package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.arnaud.cleanarchitecture.core.entity.Team;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.rabbitmq.publisher.v1.TeamEventPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;


@Aspect
@Component
public class TeamAspect {
    
	private final TeamEventPublisher teamEventPublisher;
	
	@Autowired
	public TeamAspect(final TeamEventPublisher teamEventPublisher) {
		this.teamEventPublisher = teamEventPublisher;
	}

	@After("execution(* fr.arnaud.cleanarchitecture.core.service.team.DomainTeamService.createTeam(..))  && args(team)")
    public void eventAfterCreateTeam(final JoinPoint joinPoint, final Team team) {

    	Event<TeamDto> event = 
    			new Event<>(
    					Event.StandardStatus.CREATED.name(), 
    					TeamDto.fromEntity(team));

    	this.teamEventPublisher.createTeamEvent(event);
    }

    @After("execution(* fr.arnaud.cleanarchitecture.core.service.team.DomainTeamService.updateTeam(..)) && args(id, team)")
    public void eventAfterUpdateTeam(final JoinPoint joinPoint, final UUID id, final Team team) {

    	Event<TeamDto> event = 
    			new Event<>(
    					Event.StandardStatus.UPDATED.name(), 
    					TeamDto.fromEntity(team));

    	this.teamEventPublisher.updateTeamEvent(event);
    }
    
    @After("execution(* fr.arnaud.cleanarchitecture.core.service.team.DomainTeamService.deleteTeam(..)) && args(id)")
    public void eventAfterCreateTeam(final JoinPoint joinPoint, final UUID id) {
    
    	Event<UUID> event = 
    			new Event<>(
    					Event.StandardStatus.DELETED.name(), 
    					id);

    	this.teamEventPublisher.deleteTeamEvent(event);
    }
}
