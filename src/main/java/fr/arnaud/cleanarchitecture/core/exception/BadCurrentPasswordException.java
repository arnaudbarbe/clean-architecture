package fr.arnaud.cleanarchitecture.core.exception;

public class BadCurrentPasswordException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadCurrentPasswordException(final String message) {
        super(message);
    }
}