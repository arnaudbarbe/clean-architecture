package fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import fr.arnaud.cleanarchitecture.core.entity.Player;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.Event;
import fr.arnaud.cleanarchitecture.infrastructure.configuration.eventdriven.publisher.PlayerEventPublisher;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;


@Aspect
@Configuration
public class PlayerAspect {
    
	private final PlayerEventPublisher playerEventPublisher;
	
	@Autowired
	public PlayerAspect(final PlayerEventPublisher playerEventPublisher) {
		this.playerEventPublisher = playerEventPublisher;
	}

	@After("execution(* fr.arnaud.cleanarchitecture.core.service.player.DomainPlayerService.createPlayer(..))  && args(player)")
    public void eventAfterCreatePlayer(final JoinPoint joinPoint, final Player player) {

    	Event<PlayerDto> event = 
    			new Event<>(
    					Event.StandardStatus.CREATED.name(), 
    					PlayerDto.fromEntity(player));

    	this.playerEventPublisher.createPlayerEvent(event);
    }

    @After("execution(* fr.arnaud.cleanarchitecture.core.service.player.DomainPlayerService.updatePlayer(..)) && args(player)")
    public void eventAfterUpdatePlayer(final JoinPoint joinPoint, final Player player) {

    	Event<PlayerDto> event = 
    			new Event<>(
    					Event.StandardStatus.UPDATED.name(), 
    					PlayerDto.fromEntity(player));

    	this.playerEventPublisher.updatePlayerEvent(event);
    }
	
    @After("execution(* fr.arnaud.cleanarchitecture.core.service.player.DomainPlayerService.deletePlayer(..)) && args(id)")
    public void eventAfterCreatePlayer(final JoinPoint joinPoint, final UUID id) {
    
    	Event<UUID> event = 
    			new Event<>(
    					Event.StandardStatus.DELETED.name(), 
    					id);

    	this.playerEventPublisher.deletePlayerEvent(event);
    }
}
