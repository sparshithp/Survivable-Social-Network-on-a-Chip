package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IAnnouncementDAO;
import edu.cmu.sv.ws.ssnoc.data.po.AnnouncementPO;
import edu.cmu.sv.ws.ssnoc.dto.AnnouncementDTO;

@Path("/announcement")
public class AnnouncementService extends BaseService {

//	Start memory measurement, start recording memory crumbs	       POST	/memory/start				No		Measure Memory																
//	Cancel memory measurement, stop recording memory crumbs	       POST	/memory/stop				No		Measure Memory																
//	Delete memory crumbs	                                       DELETE	/memory/				No		Measure Memory																
//	Get memory measurement for the default time interval (24 hrs)  GET	/memory/		array of MemoryCrumb		No		Measure Memory																
//	Get memory measurement for specified time interval	           GET	/memory/interval/timeWindowInHours		array of MemoryCrumb		No		Measure Memory																	

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getall")
	public List<AnnouncementDTO> getAllAnnouncements() {
		Log.enter("getting all last announcements");

		ArrayList<AnnouncementDTO> dtoList = new ArrayList<AnnouncementDTO>();
		try {
				IAnnouncementDAO dao = DAOFactory.getInstance().getAnnouncementDAO();
				ArrayList<AnnouncementPO> poList = dao.getAllAnnouncements();
				
				if (poList != null){
					for(AnnouncementPO po: poList){
						AnnouncementDTO dto = new AnnouncementDTO();
						dto.setTitle(po.getTitle());
						dto.setContent(po.getContent());
						dto.setAuthor(po.getAuthor());
						dto.setLocationDesc(po.getLocationDesc());
						dto.setPostAt(po.getPostAt().toString());
						dtoList.add(dto);
					}
			}
				

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(dtoList);
		}

		return dtoList;
	}	
		

	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/deleteall")
	public Response deleteAllAnnouncementCrumbs() {
		Log.enter("trying to delete all entries in announcement CRUMBS table");
		try {
			IAnnouncementDAO dao = DAOFactory.getInstance().getAnnouncementDAO();
			dao.deleteAllAnnouncements();			
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit("trying to delete all entries in announcement CRUMBS table from finally block");
		}
		return ok();	
	}	
	
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/insert")
	public Response insertAnnouncement(AnnouncementDTO announcementDTO) { 
		if(announcementDTO == null){
			Log.enter("empty AnnouncementDTO");
			return ok("empty AnnouncementDTO"); 
		}
		Log.enter(announcementDTO);
		
		try {
			IAnnouncementDAO dao = DAOFactory.getInstance().getAnnouncementDAO();
			
			AnnouncementPO po = new AnnouncementPO();
			po.setTitle(announcementDTO.getTitle());
			po.setContent(announcementDTO.getContent());
			po.setAuthor(announcementDTO.getAuthor());
			po.setLocationDesc(announcementDTO.getLocationDesc());
			
			dao.insertAnnouncement(po);

		}
		finally {
			Log.exit(announcementDTO);	
		}

		return ok("Announcement Inserted");
	}
	
}
