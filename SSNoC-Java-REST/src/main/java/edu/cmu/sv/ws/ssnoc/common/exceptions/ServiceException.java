package edu.cmu.sv.ws.ssnoc.common.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * This is the exception class that is raised by all Business layer classes in
 * the code base. This is used to report Internal Server Error in the RESTful
 * service code. A response code of 500 will be returned to the API caller.
 * 
 */
public class ServiceException extends CheckedException {
	private static final long serialVersionUID = 9016443772327731239L;

	/**
	 * Default constructor to raise a generic service layer exception.
	 */
	public ServiceException() {
		this("Generic Service Exception");
	}

	/**
	 * Creates a new exception with an already raised Throwable.
	 * 
	 * @param e
	 *            - Instance of Throwable with exception or error details.
	 */
	public ServiceException(Throwable e) {
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
	public ServiceException(String message, Throwable e) {
		super(message, e, Response.status(Status.CONFLICT)
				.entity(message == null ? e.getMessage() : message)
				.type(MediaType.TEXT_PLAIN_TYPE).build());
	}

	/**
	 * Throw an exception with a custom message. This can be used when raising a
	 * user defined exception from the Business layer. <br/>
	 * NOTE: Ideally Response status codes and response messages should be used.
	 * But, if we have to log an internal server failure, we can use this
	 * constructor to create and raise a custom Service Exception.
	 * 
	 * @param message
	 *            - Custom error message.
	 */
	public ServiceException(String message) {
		this(message, null);
	}
}
