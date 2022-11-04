package fr.arnaud.cleanarchitecture.core.exception;

public class BadCredentialsException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public BadCredentialsException(final String message) {
        super(message);
    }
}