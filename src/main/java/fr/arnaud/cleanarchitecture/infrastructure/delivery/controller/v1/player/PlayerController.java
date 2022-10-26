package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.player;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.arnaud.cleanarchitecture.core.service.player.PlayerService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.PlayerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/v1/players")
@Tag(name = "Player", description = "The Player API")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(final PlayerService playerService) {
        this.playerService = playerService;
    }

    
    
    
    
    
    
    
	@PostMapping( 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

	@Operation(
			summary = "Create a player", 
			description = "Create an player bla bla")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "201", 
    				description = "created",
    						content = @Content(
    						        schema = @Schema(implementation = UUID.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Player")})
    public UUID createPlayer(
    		final HttpServletResponse response, 
    		final HttpServletRequest request,
    		@RequestBody final PlayerDto player) {
		
		UUID id = this.playerService.createPlayer(player.toEntity());
		response.setHeader(HttpHeaders.LOCATION, "/v1/players" + id);		
        return id;
    }

	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{playerId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "update a Player", 
			description = "update a Player")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Player")})
    public void updatePlayer(@PathVariable final UUID playerId, @RequestBody final PlayerDto player) {
        this.playerService.updatePlayer(playerId, player.toEntity());
    }

	
	
	
	
	
	
	
	@DeleteMapping(
			value = "/{playerId}")

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "Delete a player", 
			description = "Delete a player by its identifier")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Player")})
    public void deletePlayer(@PathVariable final UUID playerId) {
        this.playerService.deletePlayer(playerId);
    }

  
	
	
	
	
	
	
	
	@GetMapping(
			value = "/{playerId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

	@Operation(
			summary = "Get a Player", 
			description = "Get a Player")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "200", 
					description = "successful operation") })

	@Tags({ 
		@Tag(name="Player")})
    public PlayerDto getPlayer(@PathVariable final UUID playerId) {
        return PlayerDto.fromEntity(this.playerService.getPlayer(playerId));
    }	
	
	
	
	
	
	
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

    @Operation(
    		summary = "Get all Player",
    		description = "Get all Player")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "200", 
    				description = "successful operation")})

	@Tags({ 
		@Tag(name="Player")})
    public List<PlayerDto> getPlayers() {
        return this.playerService.getPlayers().stream().map(PlayerDto::fromEntity).toList();
    }
}