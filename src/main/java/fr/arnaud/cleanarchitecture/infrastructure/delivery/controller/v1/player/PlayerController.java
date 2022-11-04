package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.player;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.arnaud.cleanarchitecture.core.entity.Player;
import fr.arnaud.cleanarchitecture.core.service.player.PlayerService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.ToolsController;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.model.PlayerModel;
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
public class PlayerController extends ToolsController {

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
    public ResponseEntity<UUID> createPlayer(@RequestBody final PlayerDto player) {
		
		UUID id = this.playerService.createPlayer(player.toEntity());
		
		return ResponseEntity
	    	      .status(HttpStatus.CREATED)
	    	      .headers(getLocationHeader("/v1/players" + id))
	    	      .body(id);
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
    public ResponseEntity<Void> updatePlayer(@PathVariable final UUID playerId, @RequestBody final PlayerDto player) {
        this.playerService.updatePlayer(playerId, player.toEntity());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    public ResponseEntity<Void> deletePlayer(@PathVariable final UUID playerId) {
        this.playerService.deletePlayer(playerId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    public ResponseEntity<PlayerModel> getPlayer(@PathVariable final UUID playerId) {
        Player entity = this.playerService.getPlayer(playerId);
		if (entity == null) {
			return null;
		} else {
			
			PlayerModel model = PlayerModel.fromEntity(entity)
					.add(getSelfLink(entity.getId()))
					.add(getCreateLink())
					.add(getUpdateLink(entity.getId()))
					.add(getDeleteLink(entity.getId()))
					.add(getGetAllLink());

			return ResponseEntity
		    	      .status(HttpStatus.OK)
		    	      .body(model);
		}
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
    public ResponseEntity<List<PlayerModel>> getPlayers() {
		List<PlayerModel> models = this.playerService.getPlayers().stream()
        		.map(PlayerModel::fromEntity)
        		.map(model -> model.add(getSelfLink(model.getId())))
        		.map(model -> model.add(getCreateLink()))
        		.map(model -> model.add(getUpdateLink(model.getId())))
        		.map(model -> model.add(getDeleteLink(model.getId())))
        		.map(model -> model.add(getGetAllLink()))
        		.toList();

		return ResponseEntity
	    	      .status(HttpStatus.OK)
	    	      .body(models);
    }
}