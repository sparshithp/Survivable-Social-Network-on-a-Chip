package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IShareStatusDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumbDTO;

@Path("/status")
public class ShareStatusService extends BaseService {
	
	//Update a user's status and create a breadcrumb	POST	/status/userName	updatedAt, statusCode, Location (optional)	full URI of created location breadcrumb if Location was specified in request	If new status breadcrumb is created: 201 Created	Yes		Share Status																
	//maybe need to also update statusCode filed user table
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public Response insertStatusCrumb(@PathParam("userName") String userName, StatusCrumbDTO statusCrumbDto) { //update status but actually need to insert a status.
		Log.enter(statusCrumbDto);
		//StatusCrumbDTO resp = new StatusCrumbDTO();

		try {
			//update user info in user table. should be a transaction, but now, no time for this.
			IUserDAO userdao = DAOFactory.getInstance().getUserDAO();
			
			UserPO existingUser = userdao.findByName(userName);

			if (existingUser != null) { 
					Log.trace("User found, now updating ...");
					UserPO po = new UserPO();	
					po.setUserName(userName);
					po.setLocationDesc(statusCrumbDto.getLocationDesc());
					po.setStatus(statusCrumbDto.getStatusCode());
					userdao.update(po);			
			}		
			
			//IUserDAO dao = DAOFactory.getInstance().getUserDAO();
			IShareStatusDAO dao = DAOFactory.getInstance().getShareStatusDAO();
//			UserPO existingUser = dao.findByName(user.getUserName());

			if(userName != null && statusCrumbDto != null){				
				dao.insert(ConverterUtils.convert(statusCrumbDto));				
			}
			else{
				Log.trace("empty data for insert new status crumb ...");
			}
		}
		finally {
			Log.exit(statusCrumbDto);	
		}

		return created(statusCrumbDto);
	}

	//Retrieve a status breadcrumb by unique ID	GET	/status/crumbID	StatusCrumb No Share Status																	

	//get a status info by id
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{crumbID}")
	public StatusCrumbDTO findStatusCrumbByID(@PathParam("crumbID") String crumbID) {
		Log.enter(crumbID);

		StatusCrumbDTO dto = null;
		try {
			//StatusCrumbPO po = loadExistingUser(userName);
			if(crumbID != null){
				IShareStatusDAO dao = DAOFactory.getInstance().getShareStatusDAO();
				StatusCrumbPO po = dao.findByCrumbId(crumbID);
				dto = ConverterUtils.convert(po);
			}

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(dto);
		}

		return dto;
	}
	
}
