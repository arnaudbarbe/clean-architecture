package fr.arnaud.cleanarchitecture.core.exception;

public class BadParameterException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public BadParameterException(final String message) {
        super(message);
    }
}