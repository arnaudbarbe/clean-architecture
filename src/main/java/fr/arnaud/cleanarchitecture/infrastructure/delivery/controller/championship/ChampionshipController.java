package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship;

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

import fr.arnaud.cleanarchitecture.core.service.championship.ChampionshipService;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.request.CreateChampionshipRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.request.UpdateChampionshipRequest;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.response.CreateChampionshipResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.response.GetChampionshipResponse;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.championship.response.GetChampionshipsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/championships")
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
    		value = {@ApiResponse(
    				responseCode = "201", 
    				description = "created",
    						content = @Content(
    						        schema = @Schema(implementation = CreateChampionshipResponse.class, example = "f67546f1-5a47-4e86-b7a9-dbae57fbbb57")
    						)
    				)}
    		)

	@Tags({ 
		@Tag(name="Championship")})
    public CreateChampionshipResponse createChampionship(@RequestBody final CreateChampionshipRequest createChampionshipRequest) {
        final UUID id = this.championshipService.createChampionship(createChampionshipRequest.getChampionship());

        return CreateChampionshipResponse.builder().id(id).build();
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
    public void updateChampionship(@PathVariable final UUID championshipId, @RequestBody final UpdateChampionshipRequest updateChampionshipRequest) {
        this.championshipService.updateChampionship(championshipId, updateChampionshipRequest.getChampionship());
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
		@Tag(name="Order")})
    public void deleteChampionship(@PathVariable final UUID championshipId) {
        this.championshipService.deleteChampionship(championshipId);
    }

  
	
	
	
	
	
	
	
    @GetMapping(
		value = "/{championshipId}")
    public GetChampionshipResponse getChampionship(@PathVariable final UUID championshipId) {
        return GetChampionshipResponse.builder().championship(this.championshipService.getChampionship(championshipId)).build();
    }	
	
	
	
	
	
	
	
    @GetMapping()
    public GetChampionshipsResponse getChampionships() {
        return GetChampionshipsResponse.builder().build().addOrders(this.championshipService.getChampionships());
    }
}