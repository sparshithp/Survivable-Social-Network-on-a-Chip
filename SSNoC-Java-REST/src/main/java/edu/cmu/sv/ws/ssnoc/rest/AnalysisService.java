package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IPerformanceTestDAO;
import edu.cmu.sv.ws.ssnoc.data.po.PerformanceCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.TestMsgPO;
import edu.cmu.sv.ws.ssnoc.data.util.PerformanceTestUtil;
import edu.cmu.sv.ws.ssnoc.dto.AnalysisDTO;
import edu.cmu.sv.ws.ssnoc.dto.TestMsgDTO;



@Path("/usergroups")
public class AnalysisService extends BaseService {
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/unconnected/{dur}")
	public Response getClusters(@PathParam("dur") int dur) {
	Log.enter(dur);
		AnalysisDTO dto = DAOFactory.getInstance().getAnalysisDAO().getClusters(dur);
	
		
			Log.exit(dto);	
	
		return ok(dto);
	}
	
	
}
