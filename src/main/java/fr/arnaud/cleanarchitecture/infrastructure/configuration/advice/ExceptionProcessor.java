package fr.arnaud.cleanarchitecture.infrastructure.configuration.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.datastax.oss.driver.api.core.servererrors.UnauthorizedException;

import fr.arnaud.cleanarchitecture.core.entities.DomainError;
import fr.arnaud.cleanarchitecture.core.exception.BadCurrentPasswordException;
import fr.arnaud.cleanarchitecture.core.exception.BadParameterException;
import fr.arnaud.cleanarchitecture.core.exception.BadUuidException;
import fr.arnaud.cleanarchitecture.core.exception.DomainException;
import fr.arnaud.cleanarchitecture.core.exception.EntityAlreadyExistException;
import fr.arnaud.cleanarchitecture.core.exception.EntityNotFoundException;
import fr.arnaud.cleanarchitecture.core.exception.ForbiddenException;
import fr.arnaud.cleanarchitecture.core.exception.NotAllowedException;
import fr.arnaud.cleanarchitecture.core.exception.ValidationException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@ControllerAdvice
public class ExceptionProcessor  {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionProcessor.class);
	
	@ResponseBody
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public String handleHttpMediaTypeNotAcceptableException() {
	    return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
	}
	
	@ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ApiResponse(
			responseCode = "400",
			description = "Bad Request",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"BAD_REQUEST\",\r\n  \"message\": \"Can't find method\"\r\n}")
			)
		)
	public DomainError handleConflict(final RuntimeException ex) {
		LOGGER.error("handleConflict", ex);
		return new DomainError(DomainError.ErrorCodes.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler({
		BadCurrentPasswordException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ApiResponse(
			responseCode = "400",
			description = "Bad Request",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"BAD_CURRENT_PASSWORD\",\r\n  \"message\": \"Current password doesn't match!\"\r\n}")
			)
		)
	public DomainError badCurrentPasswordWsException(final BadCurrentPasswordException ex) {
		LOGGER.error("badCurrentPasswordWsException", ex);
		return new DomainError(DomainError.ErrorCodes.BAD_CURRENT_PASSWORD, "Current password doesn't match!");
	}

	@ExceptionHandler({
		ValidationException.class,
		BadParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ApiResponse(
			responseCode = "400",
			description = "Bad Request",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"BAD_REQUEST\",\r\n  \"message\": \"The parameter from must be lower than to\"\r\n}")
			)
		)
	public DomainError badParameterWsException(final RuntimeException ex) {
		LOGGER.error("badParameterWsException", ex);
		return new DomainError(DomainError.ErrorCodes.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler({
		MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ApiResponse(
			responseCode = "400",
			description = "Bad Request",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"MISSING_PARAMETER\",\r\n  \"message\": \"Parameter 'operatorId' missing\"\r\n}")
			)
		)
	public DomainError missingServletRequestParameterException(final MissingServletRequestParameterException ex) {
		LOGGER.error("missingServletRequestParameterException", ex);
		return new DomainError(DomainError.ErrorCodes.MISSING_PARAMETER, ex.getMessage());
	}

	@ExceptionHandler({
		UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	@ApiResponse(
			responseCode = "401",
			description = "Unauthorized",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"UNAUTHORIZED\",\r\n  \"message\": \"Need to be authenticated\"\r\n}")
			)
		)
	public DomainError unauthorizedException(final UnauthorizedException ex) {
		LOGGER.error("UnauthorizedException", ex);
		return new DomainError(DomainError.ErrorCodes.UNAUTHORIZED, "Need to be authenticated");
	}

	@ExceptionHandler({
		ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	@ApiResponse(
			responseCode = "403",
			description = "Forbidden",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"FORBIDDEN\",\r\n  \"message\": \"Forbidden\"\r\n}")
			)
		)
	public DomainError forbiddenException(final ForbiddenException ex) {
		LOGGER.error("forbiddenException", ex);
		return new DomainError(DomainError.ErrorCodes.FORBIDDEN, "Forbidden");
	}

	@ExceptionHandler({
		EntityNotFoundException.class,
		BadUuidException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	@ApiResponse(
			responseCode = "404",
			description = "Not found",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"NOT_FOUND\",\r\n  \"message\": \"Object not found\"\r\n}")
			)
		)
	public DomainError objectNotFound(final RuntimeException ex) {
		LOGGER.error("objectNotFound", ex);
		return new DomainError(DomainError.ErrorCodes.NOT_FOUND, "Object not found");
	}

	@ExceptionHandler({
		NotAllowedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	@ApiResponse(
			responseCode = "405",
			description = "Method not allowed",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"NOT_ALLOWED\",\r\n  \"message\": \"Method not allowed\"\r\n}")
			)
		)
	public DomainError notAllowed(final NotAllowedException ex) {
		LOGGER.error("notAllowed", ex);
		return new DomainError(DomainError.ErrorCodes.METHOD_NOT_ALLOWED, "Method not allowed");
	}

	@ExceptionHandler({
		EntityAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	@ApiResponse(
			responseCode = "409",
			description = "Conflict",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"OBJECT_ALREADY_EXIST\",\r\n  \"message\": \"Operaror named 'Orange' already exist\"\r\n}")
			)
		)
	public DomainError entityAlreadyExistException(final EntityAlreadyExistException ex) {
		LOGGER.error("entityAlreadyExistException", ex);
		return new DomainError(DomainError.ErrorCodes.OBJECT_ALREADY_EXIST, ex.getMessage());
	}
	

	@ExceptionHandler({
		DomainException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	@ApiResponse(
			responseCode = "500",
			description = "Internal Server DomainError",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = DomainError.class, example = "{\r\n  \"code\": \"APP_FAILURE\",\r\n  \"message\": \"Can't find object with id\"\r\n}")
			)
		)
	public DomainError tbApplicativeException(final DomainException ex) {
		LOGGER.error("domainException", ex);
		return new DomainError(DomainError.ErrorCodes.APPLICATION_FAILURE, ex.getMessage());
	}
	
}
