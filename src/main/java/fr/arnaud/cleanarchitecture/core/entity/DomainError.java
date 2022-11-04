package fr.arnaud.cleanarchitecture.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

/**
 *
 * @author abarbe
 * 
 */
public class DomainError {
	public enum ErrorCodes {
		BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, MISSING_PARAMETER, APPLICATION_FAILURE, BAD_CREDENTIALS, OBJECT_ALREADY_EXIST, NOT_FOUND, METHOD_NOT_ALLOWED, TOO_MANY_REQUESTS;

	}

	@Schema(description = "The code of this error", accessMode = AccessMode.READ_ONLY, required = true)
	private ErrorCodes code;
	
	@Schema(description = "The message of this error", accessMode = AccessMode.READ_ONLY, required = true)
	private String message;

	public DomainError() {
		super();
	}

	public DomainError(final ErrorCodes code, final String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ErrorCodes getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return "{\"code\"=\"" + this.code + "\": \"message\"=\"" + this.message + "\"}";
	}
}
