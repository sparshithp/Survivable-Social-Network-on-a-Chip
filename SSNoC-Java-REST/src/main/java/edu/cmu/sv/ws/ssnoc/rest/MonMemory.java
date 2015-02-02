package edu.cmu.sv.ws.ssnoc.rest;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMonMemoryDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryCrumbPO;
import edu.cmu.sv.ws.ssnoc.dto.MemoryCrumbDTO;

@Path("/memory")
public class MonMemory extends BaseService {
	private static Timer timer;

//	Start memory measurement, start recording memory crumbs	       POST	/memory/start				No		Measure Memory																
//	Cancel memory measurement, stop recording memory crumbs	       POST	/memory/stop				No		Measure Memory																
//	Delete memory crumbs	                                       DELETE	/memory/				No		Measure Memory																
//	Get memory measurement for the default time interval (24 hrs)  GET	/memory/		array of MemoryCrumb		No		Measure Memory																
//	Get memory measurement for specified time interval	           GET	/memory/interval/timeWindowInHours		array of MemoryCrumb		No		Measure Memory																	

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	//for last 24 hours
	public List<MemoryCrumbDTO> findMemoryCrumbs() {
		Log.enter("getting memory crumbs for the last 24 hours");

		ArrayList<MemoryCrumbDTO> dtoList = new ArrayList<MemoryCrumbDTO>();
		try {
				IMonMemoryDAO dao = DAOFactory.getInstance().getMonMemoryDAO();
				ArrayList<MemoryCrumbPO> poList = dao.getCrumbsByHourInterval(Integer.toString(24));
				
				if (poList != null){
					for(MemoryCrumbPO po: poList){
						MemoryCrumbDTO dto = new MemoryCrumbDTO();
						dto.setCrumbID(po.getCrumbID());
						dto.setUsedVolatile(po.getUsedVolatile());
						dto.setRemainingVolatile(po.getRemainingVolatile());
						dto.setUsedPersistent(po.getUsedPersistent());
						dto.setRemainingPersistent(po.getRemainingPersistent());
						dto.setCreateAt(po.getCreateAt().toString());
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

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/interval/{timeWindowInHours}")
	public List<MemoryCrumbDTO> findStatusCrumbByID(@PathParam("timeWindowInHours") String timeWindowInHours) {
		Log.enter("getting memory crumbs for the interval hours");

		ArrayList<MemoryCrumbDTO> dtoList = new ArrayList<MemoryCrumbDTO>();
		try {
				IMonMemoryDAO dao = DAOFactory.getInstance().getMonMemoryDAO();
				ArrayList<MemoryCrumbPO> poList = dao.getCrumbsByHourInterval(timeWindowInHours);
				
				if (poList != null){
					for(MemoryCrumbPO po: poList){
						MemoryCrumbDTO dto = new MemoryCrumbDTO();
						dto.setCrumbID(po.getCrumbID());
						dto.setUsedVolatile(po.getUsedVolatile());
						dto.setRemainingVolatile(po.getRemainingVolatile());
						dto.setUsedPersistent(po.getUsedPersistent());
						dto.setRemainingPersistent(po.getRemainingPersistent());
						dto.setCreateAt(po.getCreateAt().toString());
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
	public Response deleteMemoryCrumbs() {
		Log.enter("trying to delete all entries in MEMORY CRUMBS table");
		try {
			IMonMemoryDAO dao = DAOFactory.getInstance().getMonMemoryDAO();
			dao.deleteAllMemoryCrumb();			
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit("trying to delete all entries in MEMORY CRUMBS table from finally block");
		}
		return ok();	
	}	
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/start")
	public Response startMonitorMemory() { //update status but actually need to insert a status.
		Log.enter("####################starting Memory Monitor####################");
			
			timer = null;
			timer = new Timer();
			
			timer.schedule(new TimerTask(){   
				
				 IMonMemoryDAO dao = DAOFactory.getInstance().getMonMemoryDAO();			
    			 MemoryCrumbPO po = new MemoryCrumbPO();
    			 //OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    			 
	            public void run() {  	                
	    			//bean.getTotalPhysicalMemorySize();
	    			//bean.getFreePhysicalMemorySize();
	                Runtime rt=Runtime.getRuntime(); 
	    			long free = rt.freeMemory()/1024;
	    			long total = rt.totalMemory()/1024;
	    			long use = total-free;

	    			long freeStorage = 0, usedStorage = 0;
	    	        File[] roots = File.listRoots();
	    			for (File file : roots) {
	    				freeStorage += file.getFreeSpace();
	    				usedStorage += file.getUsableSpace();
	    			}
	    			
	    			po.setUsedVolatile(Long.toString(use));
	    			po.setRemainingVolatile(Long.toString(free));
	    			po.setUsedPersistent(Long.toString(usedStorage));
	    			po.setRemainingPersistent(Long.toString(freeStorage));
	    			po.setCreateAt(new Timestamp(System.currentTimeMillis()));			
	    			dao.insertMemoryCrumb(po);
	    			
	    			System.out.println(po.getUsedVolatile() + "-" + po.getRemainingVolatile()+"-"+po.getCreateAt());  
	    			
	            }  
	        },0,5000);

		return ok("starting memory monitor");
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/stop")
	public Response stopMonitorMemory() { //update status but actually need to insert a status.
		Log.enter("####################Stoping Memory Monitor####################");
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		return ok("closing memory monitor");
	}	
	
}
