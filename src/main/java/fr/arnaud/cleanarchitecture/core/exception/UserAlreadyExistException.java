package fr.arnaud.cleanarchitecture.core.exception;

public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(final String message) {
        super(message);
    }
}
