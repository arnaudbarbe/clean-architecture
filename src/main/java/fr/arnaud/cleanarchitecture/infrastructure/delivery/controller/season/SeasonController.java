package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season;

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
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.request.CreateSeasonRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.request.UpdateSeasonRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.response.CreateSeasonResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.response.GetSeasonResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.season.response.GetSeasonsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/seasons")
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
    						        schema = @Schema(implementation = CreateSeasonResponse.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Season")})
    public CreateSeasonResponse createSeason(@RequestBody final CreateSeasonRequest createSeasonRequest) {
        final UUID id = this.seasonService.createSeason(createSeasonRequest.getSeason());

        return CreateSeasonResponse.builder().id(id).build();
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
    public void updateSeason(@PathVariable final UUID seasonId, @RequestBody final UpdateSeasonRequest updateSeasonRequest) {
        this.seasonService.updateSeason(seasonId, updateSeasonRequest.getSeason());
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
		@Tag(name="Order")})
    public void deleteSeason(@PathVariable final UUID seasonId) {
        this.seasonService.deleteSeason(seasonId);
    }

  
	
	
	
	
	
	
	
    @GetMapping(
		value = "/{seasonId}")
    public GetSeasonResponse getSeason(@PathVariable final UUID seasonId) {
        return GetSeasonResponse.builder().season(this.seasonService.getSeason(seasonId)).build();
    }	
	
	
	
	
	
	
	
    @GetMapping()
    public GetSeasonsResponse getSeasons() {
        return GetSeasonsResponse.builder().build().addOrders(this.seasonService.getSeasons());
    }
}