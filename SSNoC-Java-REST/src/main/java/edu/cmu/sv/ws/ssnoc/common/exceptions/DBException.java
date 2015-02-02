package edu.cmu.sv.ws.ssnoc.common.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * This is the exception class that is raised by all DAO layer classes in the
 * code base. Using this class we plan to standardize how exceptions are handled
 * between business and data tiers.
 * 
 */
public class DBException extends CheckedException {
	private static final long serialVersionUID = 7825682239858801523L;

	/**
	 * Default constructor.
	 */
	public DBException() {
		this("Generic exception from DAO layer");
	}

	/**
	 * Creates a new DBException with an already raised Throwable.
	 * 
	 * @param e
	 *            - Instance of Throwable with exception or error details.
	 */
	public DBException(Throwable e) {
		this(null, e);
	}

	/**
	 * This constructor creates a new DBException with a custom message, and a
	 * Throwable instance.
	 * 
	 * @param message
	 *            - Custom error or exception message.
	 * @param e
	 *            - Instance of Throwable with exception of error details
	 */
	public DBException(String message, Throwable e) {
		super(message, e, Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(message == null ? e.getMessage() : message)
				.type(MediaType.TEXT_PLAIN_TYPE).build());
	}

	/**
	 * Throw a DBException with a custom message. This can be used when raising
	 * a user defined exception from the DAO layer.
	 * 
	 * @param message
	 *            - Custom error message.
	 */
	public DBException(String message) {
		this(message, null);
	}
}
