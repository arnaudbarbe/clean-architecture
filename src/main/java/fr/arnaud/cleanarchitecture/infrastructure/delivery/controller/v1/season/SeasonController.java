package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.season;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.SeasonDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/v1/seasons")
@Tag(name = "Season", description = "The Season API")
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
    public UUID createSeason(
    		final HttpServletResponse response, 
    		final HttpServletRequest request,
    		@RequestBody final SeasonDto season) {
		
		UUID id = this.seasonService.createSeason(season.toEntity());
		response.setHeader(HttpHeaders.LOCATION, "/v1/seasons" + id);		
        return id;
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
    public void updateSeason(@PathVariable final UUID seasonId, @RequestBody final SeasonDto season) {
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
			value = "/{seasonId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

	@Operation(
			summary = "Get a Season", 
			description = "Get a Season")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "200", 
					description = "successful operation") })

	@Tags({ 
		@Tag(name="Season")})
    public SeasonDto getSeason(@PathVariable final UUID seasonId) {
        return SeasonDto.fromEntity(this.seasonService.getSeason(seasonId));
    }	
	
	
	
	
	
	
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

    @Operation(
    		summary = "Get all Season",
    		description = "Get all Season")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "200", 
    				description = "successful operation")})

	@Tags({ 
		@Tag(name="Season")})
    public List<SeasonDto> getSeasons() {
        return this.seasonService.getSeasons().stream().map(SeasonDto::fromEntity).toList();
    }
}