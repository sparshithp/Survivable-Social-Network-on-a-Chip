package edu.cmu.sv.ws.ssnoc.data.util;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;

/**
 * This class is the Connection Pool Factory to obtain an instance of the H2
 * Connection Pool.
 * 
 */
public class ConnectionPoolFactory {
	private static ConnectionPoolFactory instance;

	/**
	 * Singleton method to obtain the connection pool factory.
	 * 
	 * @return - ConnectionPoolFactory instance.
	 */
	public static ConnectionPoolFactory getInstance() {
		if (instance == null) {
			Log.info("Creating a new ConnectionPoolFactory singleton instance.");
			instance = new ConnectionPoolFactory();
		}

		return instance;
	}

	/**
	 * This method will return an instance of the H2 ConnectionPool.
	 * 
	 * @return - H2 ConnectionPool
	 */
	public IConnectionPool getH2ConnectionPool() {
		return H2ConnectionPoolImpl.getInstance();
	}
}
