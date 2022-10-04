package fr.arnaud.cleanarchitecture.core.exception;

public class EntityAlreadyExistException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistException(final String message) {
        super(message);
    }
}