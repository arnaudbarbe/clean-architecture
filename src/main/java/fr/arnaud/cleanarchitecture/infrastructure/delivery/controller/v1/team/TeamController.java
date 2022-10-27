package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.team;

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

import fr.arnaud.cleanarchitecture.core.entity.Team;
import fr.arnaud.cleanarchitecture.core.service.team.TeamService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.model.TeamModel;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.TeamDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/v1/teams")
@Tag(name = "Team", description = "The Team API")
public class TeamController {

    private final TeamService teamService;

    public static final String CREATE_RELATION = "create";
    public static final String UPDATE_RELATION = "update";
    public static final String GETALL_RELATION = "getAll";
    public static final String DELETE_RELATION = "delete";

    @Autowired
    public TeamController(final TeamService teamService) {
        this.teamService = teamService;
    }

    
    
    
    
    
    
    
	@PostMapping( 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

	@Operation(
			summary = "Create a team", 
			description = "Create an team bla bla")

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
		@Tag(name="Team")})
    public ResponseEntity<UUID> createTeam( @RequestBody final TeamDto team) {

		UUID id = this.teamService.createTeam(team.toEntity());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.LOCATION, "/v1/teams" + id);		
		
		return ResponseEntity
	    	      .status(HttpStatus.CREATED)
	    	      .headers(responseHeaders)
	    	      .body(id);
    }

	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{teamId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "update a Team", 
			description = "update a Team")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Team")})
    public ResponseEntity<Void> updateTeam(@PathVariable final UUID teamId, @RequestBody final TeamDto team) {
        this.teamService.updateTeam(teamId, team.toEntity());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

	
	
	
	
	
	
	
	@DeleteMapping(
			value = "/{teamId}")

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "Delete a team", 
			description = "Delete a team by its identifier")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Team")})
    public ResponseEntity<Void> deleteTeam(@PathVariable final UUID teamId) {
        this.teamService.deleteTeam(teamId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

  
	
	
	
	
	
	
	
	@GetMapping(
			value = "/{teamId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

	@Operation(
			summary = "Get a Team", 
			description = "Get a Team")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "200", 
					description = "successful operation") })

	@Tags({ 
		@Tag(name="Team")})
    public ResponseEntity<TeamModel> getTeam(@PathVariable final UUID teamId) {
        Team entity = this.teamService.getTeam(teamId);
		if (entity == null) {
			return null;
		} else {
			Link self = WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withSelfRel();
			Link create =  WebMvcLinkBuilder.linkTo(this.getClass()).withRel(CREATE_RELATION);
			Link update =  WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withRel(UPDATE_RELATION);
			Link delete =  WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withRel(DELETE_RELATION);
			Link getAll =  WebMvcLinkBuilder.linkTo(this.getClass()).withRel(GETALL_RELATION);
			
			TeamModel model = TeamModel.fromEntity(entity)
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
    		summary = "Get all Team",
    		description = "Get all Team")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "200", 
    				description = "successful operation")})

	@Tags({ 
		@Tag(name="Team")})
    public ResponseEntity<List<TeamModel>> getTeams() {
		List<TeamModel> models = this.teamService.getTeams().stream()
        		.map(TeamModel::fromEntity)
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