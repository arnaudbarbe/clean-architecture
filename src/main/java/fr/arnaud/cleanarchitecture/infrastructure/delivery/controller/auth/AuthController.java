package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.TokenDto;
import fr.arnaud.cleanarchitecture.infrastructure.delivery.dto.UserDto;
import fr.arnaud.cleanarchitecture.infrastructure.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization", description = "the Auth API used to obtain authorization to access resources.")
public class AuthController {
	
    private final AuthService authService;
    

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    
    
    
    
	@PostMapping(
			value = "/login",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

    @Operation(
    		summary = "Login",
    		description = "login with credentials and return Token if credentials are accepted")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "201", 
    				description = "created")})

	@Tags({ 
		@Tag(name="Authorization")})

	public ResponseEntity<TokenDto> login(
			@Parameter(description="The user used to login", required=true, schema=@Schema(implementation = UserDto.class))
            @RequestBody(required = true)
            final UserDto user) throws Exception {

		TokenDto token = this.authService.authenticate(user);

	    return new ResponseEntity<>(token, HttpStatus.CREATED);
	}

	
	
	
	
	
	
	
	
	
	@PostMapping(
			value = "/logout",
			consumes = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.NO_CONTENT)

    @Operation(
    		summary = "Logout",
    		description = "Logout and close currrent session")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "204", 
    				description = "no content")})

	@Tags({ 
		@Tag(name="Authorization")})

	public ResponseEntity<Void> logout(@RequestBody final TokenDto token
			) throws Exception {

		this.authService.logout(token);

	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	
	
	
	
	
	
	
	
	@PostMapping(
			value = "/refresh",
			produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseStatus(code = HttpStatus.CREATED)

    @Operation(
    		summary = "refresh the Token",
    		description = "use this method with your refresh token when your token has expired.")

    @ApiResponses(
    		value = {@ApiResponse(
    				responseCode = "201", 
    				description = "created")})

	@Tags({ 
		@Tag(name="Authorization")})

	public ResponseEntity<TokenDto> refreshToken(
			final HttpServletResponse response,
			final HttpServletRequest request,
			@Parameter(description="The refresh token", required=true, schema=@Schema(implementation = TokenDto.class))
			@RequestBody(required = true)
			final TokenDto refreshToken) throws Exception {

		TokenDto token = this.authService.refresh(refreshToken.refreshToken());

	    return new ResponseEntity<>(token, HttpStatus.CREATED);
	}

}
