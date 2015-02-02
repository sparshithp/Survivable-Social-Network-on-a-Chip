package edu.cmu.sv.ws.ssnoc.common.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ValidationException extends CheckedException {
	private static final long serialVersionUID = -5240083491809018723L;

	/**
	 * Default constructor to raise a generic validation exception.
	 */
	public ValidationException() {
		this("Generic validation exception", null);
	}

	/**
	 * Creates a new exception with an already raised Throwable.
	 * 
	 * @param e
	 *            - Instance of Throwable with exception or error details.
	 */
	public ValidationException(Throwable e) {
		this(null, e);
	}

	/**
	 * This constructor creates a new exception with a custom message, and a
	 * Throwable instance.
	 * 
	 * @param message
	 *            - Custom error or exception message.
	 * @param e
	 *            - Instance of Throwable with exception of error details
	 */
	public ValidationException(String message, Throwable e) {
		super(message, e, Response.status(Status.BAD_REQUEST)
				.entity(message == null ? e.getMessage() : message)
				.type(MediaType.TEXT_PLAIN_TYPE).build());
	}

	/**
	 * Throw an exception with a custom message. This can be used when raising a
	 * user defined exception from the Business layer. <br/>
	 * NOTE: Ideally Response status codes and response messages should be used.
	 * But, if we have to log an internal server failure, we can use this
	 * constructor to create and raise a custom Validation Exception.
	 * 
	 * @param message
	 *            - Custom error message.
	 */
	public ValidationException(String message) {
		this(message, null);
	}
}
