package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.league;

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

import fr.arnaud.cleanarchitecture.core.entity.League;
import fr.arnaud.cleanarchitecture.core.service.league.LeagueService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.ToolsController;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.model.LeagueModel;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.LeagueDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/v1/leagues")
@Tag(name = "League", description = "The League API")
public class LeagueController extends ToolsController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController(final LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    
    
    
    
    
    
    
	@PostMapping( 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

	@Operation(
			summary = "Create a league", 
			description = "Create an league bla bla")

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
		@Tag(name="League")})
    public ResponseEntity<UUID> createLeague(@RequestBody final LeagueDto league) {
		
		UUID id = this.leagueService.createLeague(league.toEntity());
		
		return ResponseEntity
	    	      .status(HttpStatus.CREATED)
	    	      .headers(getLocationHeader("/v1/leagues" + id))
	    	      .body(id);
    }

	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{leagueId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "update a League", 
			description = "update a League")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="League")})
    public ResponseEntity<Void> updateLeague(@PathVariable final UUID leagueId, @RequestBody final LeagueDto league) {
        this.leagueService.updateLeague(leagueId, league.toEntity());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

	
	
	
	
	
	
	
	@DeleteMapping(
			value = "/{leagueId}")

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "Delete a league", 
			description = "Delete a league by its identifier")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="League")})
    public ResponseEntity<Void> deleteLeague(@PathVariable final UUID leagueId) {
        this.leagueService.deleteLeague(leagueId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

  
	
	
	
	
	
	
	
	@GetMapping(
			value = "/{leagueId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

	@Operation(
			summary = "Get a League", 
			description = "Get a League")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "200", 
					description = "successful operation") })

	@Tags({ 
		@Tag(name="League")})
    public ResponseEntity<LeagueModel> getLeague(@PathVariable final UUID leagueId) {
		League entity = this.leagueService.getLeague(leagueId);
		if (entity == null) {
			return null;
		} else {
			
			LeagueModel model = LeagueModel.fromEntity(entity)
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
    		summary = "Get all League",
    		description = "Get all League")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "200", 
    				description = "successful operation")})

	@Tags({ 
		@Tag(name="League")})
    public ResponseEntity<List<LeagueModel>> getLeagues() {
		List<LeagueModel> models = this.leagueService.getLeagues().stream()
        		.map(LeagueModel::fromEntity)
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