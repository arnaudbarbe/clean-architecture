package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team;

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

import fr.arnaud.cleanarchitecture.core.service.team.TeamService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.request.CreateTeamRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.request.UpdateTeamRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.response.CreateTeamResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.response.GetTeamResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.team.response.GetTeamsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

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
    						        schema = @Schema(implementation = CreateTeamResponse.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Team")})
    public CreateTeamResponse createTeam(@RequestBody final CreateTeamRequest createTeamRequest) {
        final UUID id = this.teamService.createTeam(createTeamRequest.getTeam());

        return CreateTeamResponse.builder().id(id).build();
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
    public void updateTeam(@PathVariable final UUID teamId, @RequestBody final UpdateTeamRequest updateTeamRequest) {
        this.teamService.updateTeam(teamId, updateTeamRequest.getTeam());
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
		@Tag(name="Order")})
    public void deleteTeam(@PathVariable final UUID teamId) {
        this.teamService.deleteTeam(teamId);
    }

  
	
	
	
	
	
	
	
    @GetMapping(
		value = "/{teamId}")
    public GetTeamResponse getTeam(@PathVariable final UUID teamId) {
        return GetTeamResponse.builder().team(this.teamService.getTeam(teamId)).build();
    }	
	
	
	
	
	
	
	
    @GetMapping()
    public GetTeamsResponse getTeams() {
        return GetTeamsResponse.builder().build().addTeams(this.teamService.getTeams());
    }
}