package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.v1;

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

import fr.arnaud.cleanarchitecture.core.service.season.SeasonService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.model.v1.Season;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/v1/seasons")
public class SeasonController {

    private final SeasonService seasonService;

    @Autowired
    public SeasonController(final SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    
    
    
    
    
    
    
	@PostMapping( 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

	@Operation(
			summary = "Create a season", 
			description = "Create an season bla bla")

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
		@Tag(name="Season")})
    public UUID createSeason(@RequestBody final Season season) {
        return this.seasonService.createSeason(season.toEntity());
    }

	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{seasonId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "update a Season", 
			description = "update a Season")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Season")})
    public void updateSeason(@PathVariable final UUID seasonId, @RequestBody final Season season) {
        this.seasonService.updateSeason(seasonId, season.toEntity());
    }

	
	
	
	
	
	
	
	@DeleteMapping(
			value = "/{seasonId}")

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "Delete a season", 
			description = "Delete a season by its identifier")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Season")})
    public void deleteSeason(@PathVariable final UUID seasonId) {
        this.seasonService.deleteSeason(seasonId);
    }

  
	
	
	
	
	
	
	
    @GetMapping(
		value = "/{seasonId}")
    public Season getSeason(@PathVariable final UUID seasonId) {
        return Season.fromEntity(this.seasonService.getSeason(seasonId));
    }	
	
	
	
	
	
	
	
    @GetMapping()
    public List<Season> getSeasons() {
        return this.seasonService.getSeasons().stream().map(Season::fromEntity).toList();
    }
}