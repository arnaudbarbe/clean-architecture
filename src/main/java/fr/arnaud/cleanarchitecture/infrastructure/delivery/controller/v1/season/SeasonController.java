package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.season;

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

import fr.arnaud.cleanarchitecture.core.entity.Season;
import fr.arnaud.cleanarchitecture.core.service.season.SeasonService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.model.SeasonModel;
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

    public static final String CREATE_RELATION = "create";
    public static final String UPDATE_RELATION = "update";
    public static final String GETALL_RELATION = "getAll";
    public static final String DELETE_RELATION = "delete";

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
    public ResponseEntity<UUID> createSeason(@RequestBody final SeasonDto season) {
		
		UUID id = this.seasonService.createSeason(season.toEntity());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.LOCATION, "/v1/seasons" + id);		
		
		return ResponseEntity
	    	      .status(HttpStatus.CREATED)
	    	      .headers(responseHeaders)
	    	      .body(id);
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
    public ResponseEntity<Void> updateSeason(@PathVariable final UUID seasonId, @RequestBody final SeasonDto season) {
        this.seasonService.updateSeason(seasonId, season.toEntity());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    public ResponseEntity<Void> deleteSeason(@PathVariable final UUID seasonId) {
        this.seasonService.deleteSeason(seasonId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    public ResponseEntity<SeasonModel> getSeason(@PathVariable final UUID seasonId) {
        Season entity = this.seasonService.getSeason(seasonId);
		if (entity == null) {
			return null;
		} else {
			Link self = WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withSelfRel();
			Link create =  WebMvcLinkBuilder.linkTo(this.getClass()).withRel(CREATE_RELATION);
			Link update =  WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withRel(UPDATE_RELATION);
			Link delete =  WebMvcLinkBuilder.linkTo(this.getClass()).slash(entity.getId()).withRel(DELETE_RELATION);
			Link getAll =  WebMvcLinkBuilder.linkTo(this.getClass()).withRel(GETALL_RELATION);
			
			SeasonModel model = SeasonModel.fromEntity(entity)
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
    		summary = "Get all Season",
    		description = "Get all Season")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "200", 
    				description = "successful operation")})

	@Tags({ 
		@Tag(name="Season")})
    public ResponseEntity<List<SeasonModel>> getSeasons() {
		List<SeasonModel> models = this.seasonService.getSeasons().stream()
        		.map(SeasonModel::fromEntity)
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