package edu.cmu.sv.ws.ssnoc.common.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedUserException extends CheckedException {
	private static final long serialVersionUID = -6940340725011302810L;

	/**
	 * Default constructor to raise an unauthorized user validation exception.
	 */
	public UnauthorizedUserException(String userName) {
		super("Unauthorized User: " + userName, null, Response
				.status(Status.UNAUTHORIZED)
				.entity("Unauthorized User: " + userName)
				.type(MediaType.TEXT_PLAIN_TYPE).build());
	}
}
