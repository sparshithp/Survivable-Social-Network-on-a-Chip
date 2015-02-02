package edu.cmu.sv.ws.ssnoc.rest;

import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;

public class SSNoCAppInitializer extends HttpServlet {
	private static final long serialVersionUID = 5446123041570390878L;

	public void init(ServletConfig config) throws ServletException {
		// Perform any steps needed during server startup as part
		// of this method. This includes initializing database etc.
		try {
			DBUtils.initializeDatabase();
		} catch (SQLException e) {
			Log.error("Oops :( We ran into an error when trying to intialize "
					+ "database. Please check the trace for more details.", e);
		}
	}

}
