package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of User information in the system.
 * 
 */

public interface IShareStatusDAO {

		void insert(StatusCrumbPO statusCrumbPO);

		//void update(StatusCrumbPO statusCrumbPO);

		List<StatusCrumbPO> loadStatusCrumbsByName(String userName);

		StatusCrumbPO findByCrumbId(String crumbId);

}
