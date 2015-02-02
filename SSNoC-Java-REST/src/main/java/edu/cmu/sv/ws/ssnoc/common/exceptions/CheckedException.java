package edu.cmu.sv.ws.ssnoc.common.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CheckedException  extends WebApplicationException {
	private static final long serialVersionUID = -7161834422688964657L;

	public CheckedException(final String message, final Throwable cause, final Response response) {
        super(message, cause, response);
    }
}
