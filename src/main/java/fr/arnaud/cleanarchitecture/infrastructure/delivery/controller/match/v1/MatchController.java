package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.match.v1;

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

import fr.arnaud.cleanarchitecture.core.service.match.MatchService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.MatchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/v1/matchs")
@Tag(name = "Match", description = "The Match API")
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
    						        schema = @Schema(implementation = UUID.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Match")})
    public UUID createMatch(@RequestBody final MatchDto match) {
        return this.matchService.createMatch(match.toEntity());
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
    public void updateMatch(@PathVariable final UUID matchId, @RequestBody final MatchDto match) {
        this.matchService.updateMatch(matchId, match.toEntity());
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
		@Tag(name="Match")})
    public void deleteMatch(@PathVariable final UUID matchId) {
        this.matchService.deleteMatch(matchId);
    }

  
	
	
	
	
	
	
	
	@GetMapping(
			value = "/{matchId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

	@Operation(
			summary = "Get a Match", 
			description = "Get a Match")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "200", 
					description = "successful operation") })

	@Tags({ 
		@Tag(name="Match")})
    public MatchDto getMatch(@PathVariable final UUID matchId) {
        return MatchDto.fromEntity(this.matchService.getMatch(matchId));
    }	
	
	
	
	
	
	
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

    @Operation(
    		summary = "Get all Match",
    		description = "Get all Match")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "200", 
    				description = "successful operation")})

	@Tags({ 
		@Tag(name="Match")})
    public List<MatchDto> getMatchs() {
        return this.matchService.getMatchs().stream().map(MatchDto::fromEntity).toList();
    }
}