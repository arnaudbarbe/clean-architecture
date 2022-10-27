package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.league;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
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
public class LeagueController {

    private final LeagueService leagueService;

    public static final String CREATE_RELATION = "create";
    public static final String UPDATE_RELATION = "update";
    public static final String GETALL_RELATION = "getAll";
    public static final String DELETE_RELATION = "delete";

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
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.LOCATION, "/v1/leagues" + id);
		
		return ResponseEntity
	    	      .status(HttpStatus.CREATED)
	    	      .headers(responseHeaders)
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
			Link self = WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withSelfRel();
			Link create =  WebMvcLinkBuilder.linkTo(this.getClass()).withRel(CREATE_RELATION);
			Link update =  WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withRel(UPDATE_RELATION);
			Link delete =  WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withRel(DELETE_RELATION);
			Link getAll =  WebMvcLinkBuilder.linkTo(this.getClass()).withRel(GETALL_RELATION);
			
			LeagueModel model = LeagueModel.fromEntity(entity)
					.add(self)
					.add(create)
					.add(update)
					.add(delete)
					.add(getAll);
			
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
        		//self
        		.map(model -> model.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(model.getId()).withSelfRel()))
        		//create
        		.map(model -> model.add(WebMvcLinkBuilder.linkTo(this.getClass()).withRel(CREATE_RELATION)))
        		//update
        		.map(model -> model.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(model.getId()).withRel(UPDATE_RELATION)))
        		//delete
        		.map(model -> model.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(model.getId()).withRel(DELETE_RELATION)))
        		//getAll
        		.map(model -> model.add(WebMvcLinkBuilder.linkTo(this.getClass()).withRel(GETALL_RELATION)))
        		.toList();

		return ResponseEntity
	    	      .status(HttpStatus.OK)
	    	      .body(models);
    }
}