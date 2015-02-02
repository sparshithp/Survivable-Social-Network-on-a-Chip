package edu.cmu.sv.ws.ssnoc.common.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnknownUserException extends CheckedException {
	private static final long serialVersionUID = -7515308374902778684L;

	/**
	 * Default constructor to raise an unknown user validation exception.
	 */
	public UnknownUserException(String userName) {
		super("Unknown User: " + userName, null, Response
				.status(Status.NOT_FOUND)
				.entity("Unauthorized User: " + userName)
				.type(MediaType.TEXT_PLAIN_TYPE).build());
	}
}
