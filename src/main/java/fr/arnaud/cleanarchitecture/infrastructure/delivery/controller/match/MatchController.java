package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match;

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

import fr.arnaud.cleanarchitecture.core.model.Match;
import fr.arnaud.cleanarchitecture.core.service.match.MatchService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.request.CreateMatchRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.request.UpdateMatchRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.response.CreateMatchResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.response.GetMatchResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.response.GetMatchsResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.response.UpdateMatchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/matchs")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(final MatchService matchService) {
        this.matchService = matchService;
    }

    
    
    
    
    
    
    
	@PostMapping( 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

	@Operation(
			summary = "Create a match", 
			description = "Create an match bla bla")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "201", 
    				description = "created",
    						content = @Content(
    						        schema = @Schema(implementation = CreateMatchResponse.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Match")})
    public CreateMatchResponse createMatch(@RequestBody final CreateMatchRequest createMatchRequest) {
        final UUID id = this.matchService.createMatch(createMatchRequest.getMatch());

        return CreateMatchResponse.builder().id(id).build();
    }

	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{matchId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "update a Match", 
			description = "update a Match")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Match")})
    public UpdateMatchResponse updateMatch(@PathVariable final UUID matchId, @RequestBody final UpdateMatchRequest updateMatchRequest) {
        Match match = this.matchService.updateMatch(matchId, updateMatchRequest.getMatch());

		return UpdateMatchResponse.builder().match(match).build();
    }

	
	
	
	
	
	
	
	@DeleteMapping(
			value = "/{matchId}")

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "Delete a match", 
			description = "Delete a match by its identifier")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Order")})
    public void deleteMatch(@PathVariable final UUID matchId) {
        this.matchService.deleteMatch(matchId);
    }

  
	
	
	
	
	
	
	
    @GetMapping(
		value = "/{matchId}")
    public GetMatchResponse getMatch(@PathVariable final UUID matchId) {
        return GetMatchResponse.builder().match(this.matchService.getMatch(matchId)).build();
    }	
	
	
	
	
	
	
	
    @GetMapping()
    public GetMatchsResponse getMatchs() {
        return GetMatchsResponse.builder().build().addOrders(this.matchService.getMatchs());
    }
}