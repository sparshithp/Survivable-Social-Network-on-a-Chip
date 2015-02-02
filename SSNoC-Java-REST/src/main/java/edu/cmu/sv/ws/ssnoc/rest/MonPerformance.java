package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IPerformanceTestDAO;
import edu.cmu.sv.ws.ssnoc.data.po.PerformanceCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.TestMsgPO;
import edu.cmu.sv.ws.ssnoc.data.util.PerformanceTestUtil;
import edu.cmu.sv.ws.ssnoc.dto.TestMsgDTO;



@Path("/performance")
public class MonPerformance extends BaseService {
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/setup")
	public Response pfmSetup() { 
		Log.enter("performance test setting up");

		try {
			IPerformanceTestDAO dao = DAOFactory.getInstance().getPerformanceDAO();	
			dao.createTestMsgTable();
			System.out.println("############### TEST MSG TABLE CREATED");
		}
		finally {
			Log.exit("TestMsgTable created");	
		}

		PerformanceTestUtil.getsAmount = 0;
		PerformanceTestUtil.postsAmount = 0;
		
		return ok("TestMessageTableCreated");
		
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/teardown")
	public Response pfmTeardown() { //update status but actually need to insert a status.
		Log.enter("performance test tearing down");
		
		try {
			IPerformanceTestDAO dao = DAOFactory.getInstance().getPerformanceDAO();	
// Hakan said we do not need to store the request number in the back - DELETE - START			
//			PerformanceCrumbPO po = new PerformanceCrumbPO();
//			po.setPostsTotal(PerformanceTestUtil.postsAmount);
//			po.setGetsTotal(PerformanceTestUtil.getsAmount);
//			dao.insertPerformanceCrumb(po);
// Hakan said we do not need to store the request number in the back - DELETE - END	
			dao.deleteTestMsgTable();
			System.out.println("############### TEST MSG TABLE DELETED");
		}
		finally {
			Log.exit("table deleted");	
		}

		return ok("table deleted");
		
		
	}
	
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	//@XmlElementWrapper(name = "testMessages")
	@Path("/gettestmessage")
	public List<TestMsgDTO> getTestMsgs() {
		Log.enter();

		List<TestMsgDTO> msgs = null;
		try {
			List<TestMsgPO> msgpos = DAOFactory.getInstance().getPerformanceDAO().getTestMsg();

			msgs = new ArrayList<TestMsgDTO>();
			TestMsgDTO dto = null;
			for (TestMsgPO po : msgpos) {				
				dto = new TestMsgDTO();
				dto.setMessageID(po.getMessageID());
				dto.setAuthor(po.getAuthor());
				dto.setTarget(po.getTarget());
				dto.setContent(po.getContent());
				dto.setMessageType(po.getMessageType());
				dto.setPostedAt(po.getPostedAt().toString());
				msgs.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(msgs);
		}
		
		PerformanceTestUtil.getsAmount += 1;
		System.out.println("############### gets request number"+ PerformanceTestUtil.postsAmount);
		//PerformanceTestUtil.postsAmount = 0;
		return msgs;
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/insertmessage")
	public Response insertTestMsg(TestMsgDTO testMsgDTO) { 
		if(testMsgDTO == null){
			Log.enter("empty testMsgDTO");
			return ok("empty testMsgDTO"); 
		}
		Log.enter(testMsgDTO);
		
		try {
			IPerformanceTestDAO dao = DAOFactory.getInstance().getPerformanceDAO();	
			
			TestMsgPO po = new TestMsgPO();
			po.setAuthor(testMsgDTO.getAuthor());
			po.setContent(testMsgDTO.getContent());
			po.setTarget(testMsgDTO.getTarget());
			po.setMessageType(testMsgDTO.getMessageType());
			
			dao.insertTestMsg(po);

		}
		finally {
			Log.exit(testMsgDTO);	
		}
		//PerformanceTestUtil.getsAmount = 0;
		PerformanceTestUtil.postsAmount += 1;
		System.out.println("############### post request number"+ PerformanceTestUtil.postsAmount);
		//if memory is enough, return "msg inserted" else "Out Of Memory"
		return ok("Message Inserted");
	}
	
	@GET
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getperformancecrumb")
	public Response getPerformanceCrumb() { 
		Log.enter("going to get performance crumb");
		PerformanceCrumbPO po = null;
		try {
			IPerformanceTestDAO dao = DAOFactory.getInstance().getPerformanceDAO();			
			po = dao.getPerformanceCrumb();
		}
		finally {
			Log.exit(po);	
		}

		return ok(po);
	}
	
	
}
