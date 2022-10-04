package fr.arnaud.cleanarchitecture.core.exception;

public class EntityNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(final String message) {
        super(message);
    }
}