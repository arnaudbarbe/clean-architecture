package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1.championship;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.v1.ChampionshipDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
@RestController
@RequestMapping("/v1/championships")
@Tag(name = "Championship", description = "The Championship API")
public class ChampionshipController {

    private final ChampionshipService championshipService;

    @Autowired
    public ChampionshipController(final ChampionshipService championshipService) {
        this.championshipService = championshipService;
    }

    
    
    
    
    
    
    
	@PostMapping( 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

	@Operation(
			summary = "Create a championship", 
			description = "Create an championship bla bla")

    @ApiResponses(
    		value = {
    			@ApiResponse(
	    			responseCode = "201", 
	    			description = "created",
					content = @Content(
							schema = @Schema(implementation = UUID.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
					)
    			)}
    		)
	@Tags({ 
		@Tag(name="Championship")})
    public UUID createChampionship(
    		final HttpServletResponse response, 
    		final HttpServletRequest request,
    		@RequestBody final ChampionshipDto championship) {
		
		UUID id = this.championshipService.createChampionship(championship.toEntity());
		response.setHeader(HttpHeaders.LOCATION, "/v1/championships/" + id);
        return id;
    }

	
	
	
	
	
	
	
	
	@PutMapping(
			value = "/{championshipId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "update a Championship", 
			description = "update a Championship")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Championship")})
    public void updateChampionship(@PathVariable final UUID championshipId, @RequestBody final ChampionshipDto championship) {
        this.championshipService.updateChampionship(championshipId, championship.toEntity());
    }

	
	
	
	
	
	
	
	@DeleteMapping(
			value = "/{championshipId}")

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

	@Operation(
			summary = "Delete a championship", 
			description = "Delete a championship by its identifier")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "204", 
					description = "no content") })

	@Tags({ 
		@Tag(name="Championship")})
    public void deleteChampionship(@PathVariable final UUID championshipId) {
        this.championshipService.deleteChampionship(championshipId);
    }

  
	
	
	
	
	
	
	@GetMapping(
			value = "/{championshipId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

	@Operation(
			summary = "Get a Championship", 
			description = "Get a Championship")

	@ApiResponses(
			value = { @ApiResponse(
					responseCode = "200", 
					description = "successful operation") })

	@Tags({ 
		@Tag(name="Championship")})
    public ChampionshipDto getChampionship(@PathVariable final UUID championshipId) {
		ChampionshipDto dto = ChampionshipDto.fromEntity(this.championshipService.getChampionship(championshipId));
		if (dto != null) {
			dto.add(WebMvcLinkBuilder.linkTo(ChampionshipController.class).slash(dto.getId()).withSelfRel());
		}
		return dto;
    }	
	
	
	
	
	
	
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.OK)

    @Operation(
    		summary = "Get all Championships",
    		description = "Get all Championships")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "200", 
    				description = "successful operation")})

	@Tags({ 
		@Tag(name="Championship")})
    public List<ChampionshipDto> getChampionships() {
        return this.championshipService.getChampionships().stream().map(ChampionshipDto::fromEntity).toList();
    }
}