package fr.arnaud.cleanarchitecture.core.exception;

public class BadUuidException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadUuidException(final String message) {
        super(message);
    }
}