package edu.cmu.sv.ws.ssnoc.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserProfileAdminDAO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserProfileAdminPO;
import edu.cmu.sv.ws.ssnoc.dto.UserProfileAdminDTO;

@Path("/userprofileadmin")
public class UserProfileAdminService extends BaseService {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getProfileByUserId/{userId}")
	public UserProfileAdminDTO getAuthorizationProfileById(@PathParam("userId") String userId) {
		Log.enter("getting Authorization Profile By Id");
		UserProfileAdminDTO dto = null;	
		try {
			IUserProfileAdminDAO dao = DAOFactory.getInstance().getUserProfileAdminDAO();
			UserProfileAdminPO po = dao.getProfile(Long.parseLong(userId));
				if (po != null){
					dto = new UserProfileAdminDTO();		
					dto.setUserId(po.getUserId());
					dto.setUserName(po.getUserName());
					dto.setPassword(po.getPassword());
					dto.setSalt(po.getSalt());
					dto.setPrivilegeLevel(po.getPrivilegeLevel());
					dto.setAccountStatus(po.getAccountStatus());		
			}	

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(dto);
		}

		return dto;
	}	
		
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getProfileByUserName/{userName}")
	public UserProfileAdminDTO getAuthorizationProfileByName(@PathParam("userName") String userName) {
		Log.enter("getting Authorization Profile By User Name");
		UserProfileAdminDTO dto = null;	
		try {
			IUserProfileAdminDAO dao = DAOFactory.getInstance().getUserProfileAdminDAO();
			UserProfileAdminPO po = dao.getProfile(userName);
				if (po != null){
					dto = new UserProfileAdminDTO();		
					dto.setUserId(po.getUserId());
					dto.setUserName(po.getUserName());
					dto.setSalt(po.getSalt());
					dto.setPrivilegeLevel(po.getPrivilegeLevel());
					dto.setAccountStatus(po.getAccountStatus());		
			}	

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(dto);
		}

		return dto;
	}	
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/insert")
	public Response insertAuthorization(UserProfileAdminDTO dto) { 
		if(dto == null){
			Log.enter("empty UserProfileAdminDTO");
			return ok("empty UserProfileAdminDTO"); 
		}
		Log.enter(dto);
		
		try {
			IUserProfileAdminDAO dao = DAOFactory.getInstance().getUserProfileAdminDAO();
			
			UserProfileAdminPO po = new UserProfileAdminPO();
			po.setUserId(dto.getUserId());
			po.setUserName(dto.getUserName());
			po.setSalt(dto.getSalt());
			po.setPrivilegeLevel(dto.getPrivilegeLevel());
			po.setAccountStatus(dto.getAccountStatus());		
			dao.insertNewProfile(po);

		}
		finally {
			Log.exit(dto);	
		}

		return ok("Authorization Info Inserted");
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/update")
	public Response updateAuthorization(UserProfileAdminDTO dto) { 
		if(dto == null){
			Log.enter("empty UserProfileAdminDTO");
			return ok("empty UserProfileAdminDTO"); 
		}
		Log.enter(dto);
		
		try {
			IUserProfileAdminDAO dao = DAOFactory.getInstance().getUserProfileAdminDAO();
			
			UserProfileAdminPO po = new UserProfileAdminPO();
			po.setUserId(dto.getUserId());
			po.setUserName(dto.getUserName());
			po.setPrivilegeLevel(dto.getPrivilegeLevel());
			po.setAccountStatus(dto.getAccountStatus());
			if(dto.getPassword().equals(""))
			{
				IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();
				UserPO userPO = userDAO.findById(dto.getUserId());
				po.setPassword(userPO.getPassword());
				po.setSalt(userPO.getSalt());
			}
			else
			{
				po.setPassword(dto.getPassword());
				po = SSNCipher.encryptPassword(po);
			}
			dao.updateProfile(po);

		}
		finally {
			Log.exit(dto);	
		}

		return ok("Authorization Info Inserted");
	}
	
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/deleteAll")
	public Response deleteAllAuthorzationEntries() {
		Log.enter("trying to delete all entries in Authorzation table");
		try {
			IUserProfileAdminDAO dao = DAOFactory.getInstance().getUserProfileAdminDAO();
			dao.deleteAllAuthorizationTable();			
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit("trying to delete all entries in Authorzation table from finally block");
		}
		return ok();	
	}
	
}
