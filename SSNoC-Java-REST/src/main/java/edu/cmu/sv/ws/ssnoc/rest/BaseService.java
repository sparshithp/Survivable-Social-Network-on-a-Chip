package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.cmu.sv.ws.ssnoc.common.exceptions.CheckedException;
import edu.cmu.sv.ws.ssnoc.common.exceptions.ServiceException;
import edu.cmu.sv.ws.ssnoc.common.exceptions.UnknownUserException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

public class BaseService {
	protected void handleException(Exception e) {
		Log.error(e);
		if (e instanceof CheckedException) {
			throw (CheckedException) e;
		} else {
			throw new ServiceException(e);
		}
	}

	protected Response ok() {
		return Response.status(Status.OK).build();
	}

	protected Response ok(Object obj) {
		return Response.status(Status.OK).entity(obj).build();
	}
	
	protected Response created(Object obj) {
		return Response.status(Status.CREATED).entity(obj).build();
	}

	protected UserPO loadExistingUser(String userName) {
		UserPO po = DAOFactory.getInstance().getUserDAO().findByName(userName);
		if (po == null) {
			throw new UnknownUserException(userName);
		}

		return po;
	}
}
