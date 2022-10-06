package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.request.CreatePlayerRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.request.UpdatePlayerRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.response.CreatePlayerResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.response.GetPlayerResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.player.response.GetPlayersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/players")
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
    						        schema = @Schema(implementation = CreatePlayerResponse.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Player")})
    public CreatePlayerResponse createPlayer(@RequestBody final CreatePlayerRequest createPlayerRequest) {
        final UUID id = this.playerService.createPlayer(createPlayerRequest.getPlayer());

        return CreatePlayerResponse.builder().id(id).build();
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
    public void updatePlayer(@PathVariable final UUID playerId, @RequestBody final UpdatePlayerRequest updatePlayerRequest) {
        this.playerService.updatePlayer(playerId, updatePlayerRequest.getPlayer());
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
		@Tag(name="Order")})
    public void deletePlayer(@PathVariable final UUID playerId) {
        this.playerService.deletePlayer(playerId);
    }

  
	
	
	
	
	
	
	
    @GetMapping(
		value = "/{playerId}")
    public GetPlayerResponse getPlayer(@PathVariable final UUID playerId) {
        return GetPlayerResponse.builder().player(this.playerService.getPlayer(playerId)).build();
    }	
	
	
	
	
	
	
	
    @GetMapping()
    public GetPlayersResponse getPlayers() {
        return GetPlayersResponse.builder().build().addOrders(this.playerService.getPlayers());
    }
}