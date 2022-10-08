package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.league.v1;

import java.util.List;
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

import fr.arnaud.cleanarchitecture.core.service.league.LeagueService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.League;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/v1/leagues")
public class LeagueController {

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
    public UUID createLeague(@RequestBody final League league) {
        return this.leagueService.createLeague(league.toEntity());
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
    public void updateLeague(@PathVariable final UUID leagueId, @RequestBody final League league) {
        this.leagueService.updateLeague(leagueId, league.toEntity());
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
    public void deleteLeague(@PathVariable final UUID leagueId) {
        this.leagueService.deleteLeague(leagueId);
    }

  
	
	
	
	
	
	
	
    @GetMapping(
		value = "/{leagueId}")
    public League getLeague(@PathVariable final UUID leagueId) {
        return League.fromEntity(this.leagueService.getLeague(leagueId));
    }	
	
	
	
	
	
	
	
    @GetMapping()
    public List<League> getLeagues() {
        return this.leagueService.getLeagues().stream().map(League::fromEntity).toList();
    }
}