package edu.cmu.sv.ws.ssnoc.test;
import static com.eclipsesource.restfuse.Assert.assertBadRequest;
import static com.eclipsesource.restfuse.Assert.assertOk;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.dao.BaseDAOImpl;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.dto.AnalysisDTO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.AnalysisService;

@RunWith(HttpJUnitRunner.class)
public class AnalyzeNetworkTest extends BaseDAOImpl{
	User userA, userB, userC,userD, userE;
	 	@Before 
	    public void setUpTestData() throws Exception{

	        Connection conn= getConnection();
	        
	        PreparedStatement delUsers = conn.prepareStatement("Delete from SSN_USERS where user_name!='admin'");
	        delUsers.execute();
	        
	        PreparedStatement stmtInsertUser = conn.prepareStatement(SQL.INSERT_USER);
	        stmtInsertUser.setString(1, "A");
	        stmtInsertUser.setString(2, null);
	        stmtInsertUser.setString(3, null);
	        stmtInsertUser.execute();
	        stmtInsertUser.setString(1, "B");
	        stmtInsertUser.setString(2, null);
	        stmtInsertUser.setString(3, null);
	        stmtInsertUser.execute();
	        stmtInsertUser.setString(1, "C");
	        stmtInsertUser.setString(2, null);
	        stmtInsertUser.setString(3, null);
	        stmtInsertUser.execute();
	        stmtInsertUser.setString(1, "D");
	        stmtInsertUser.setString(2, null);
	        stmtInsertUser.setString(3, null);
	        stmtInsertUser.execute();
	        stmtInsertUser.setString(1, "E");
	        stmtInsertUser.setString(2, null);
	        stmtInsertUser.setString(3, null);
	        stmtInsertUser.execute();
	        

	        
	        System.out.println("sd");

	    }
	 	
	 	@Test
	    public void loadAllUsersAndTestImmediately() {
	        AnalysisService analysisTest = new AnalysisService();
	        ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();
	        ArrayList<String> ls = new ArrayList<String>();
	        
	        ls.add("A");
	        ls.add("B");
	        ls.add("C");
	        ls.add("D");
	        ls.add("E");
	        testData.add(ls);
	        
	        AnalysisDTO dto = DAOFactory.getInstance().getAnalysisDAO().getClusters(0);
	        assertEquals(dto.getClusters(),testData);

	    }
	 	
	 	@Test
	 	public void noChatsBetweenAnyUser(){
	 		AnalysisService analysisTest = new AnalysisService();
	        ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();
	        ArrayList<String> ls = new ArrayList<String>();
	       
	        ls.add("A");
	        ls.add("B");
	        ls.add("C");
	        ls.add("D");
	        ls.add("E");
	        
	        
	        testData.add(ls);
	        AnalysisDTO dto = DAOFactory.getInstance().getAnalysisDAO().getClusters(1);

	        assertEquals(dto.getClusters(), testData);
	        
	 	}
	 	
	 	@Test
	 	public void allUsersChatEachOther() throws SQLException{
	 		AnalysisService analysisTest = new AnalysisService();
	        ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();
	       
	        postTestPrivateMessage("admin","A","Ya");
	        postTestPrivateMessage("admin","B","Ya");
	        postTestPrivateMessage("admin","C","Ya");
	        postTestPrivateMessage("admin","D","Ya");
	        postTestPrivateMessage("admin","E","Ya");
	        postTestPrivateMessage("A","B","Ya");
	        postTestPrivateMessage("A","C","Ya");
	        postTestPrivateMessage("A","D","Ya");
	        postTestPrivateMessage("A","E","Ya");
	        postTestPrivateMessage("B","C","Ya");
	        postTestPrivateMessage("B","D","Ya");
	        postTestPrivateMessage("B","E","Ya");
	        postTestPrivateMessage("C","D","Ya");
	        postTestPrivateMessage("C","E","Ya");
	        postTestPrivateMessage("D","E","Ya");
	        
	        AnalysisDTO dto = DAOFactory.getInstance().getAnalysisDAO().getClusters(0);
	        
	        assertEquals(testData, dto.getClusters());
	 	}
	 	
	 	@Test
	 	public void partialUsersChatWithEachOther(){
	 	    
	 	   AnalysisService analysisTest = new AnalysisService();
           ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>();
           
	 	   postTestPrivateMessage("admin","A","Ya");
           postTestPrivateMessage("admin","B","Ya");
           postTestPrivateMessage("admin","C","Ya");
           postTestPrivateMessage("admin","D","Ya");
           postTestPrivateMessage("admin","E","Ya");
           postTestPrivateMessage("A","B","Ya");
           postTestPrivateMessage("A","C","Ya");
           postTestPrivateMessage("A","D","Ya");
           postTestPrivateMessage("A","E","Ya");
           
           ArrayList<String> cluster1 = new ArrayList<String>();
           cluster1.add("B");
           cluster1.add("C");
           cluster1.add("D");
           cluster1.add("E");
           
           testData.add(cluster1);
           AnalysisDTO dto = DAOFactory.getInstance().getAnalysisDAO().getClusters(0);
           
           assertEquals(testData, dto.getClusters());

           
	 	}
	 	
	 	
	 	@After
	    public void clearTestData() throws Exception{
	        Connection conn= getConnection();
	        String dropTable = "Delete from SSN_USERS where user_name='A' or user_name ='B' or user_name ='C' or user_name ='D' or user_name ='E'; Delete from SSN_MESSAGE where postedAt = '2014-02-02 12:12:12'";
	        PreparedStatement stmtDrop = conn.prepareStatement(dropTable);
	        stmtDrop.execute();

	        
	    }
	 	
	 	public static void postTestPrivateMessage(String sender, String receiver, String content)
		{
			Message message = new Message();
			message.setAuthor(sender);
			message.setContent(content);
			message.setTarget(receiver);
			message.setMessageType("CHAT");
			message.setPostedAt("2014-02-02 12:12:12");
			MessagePO po = new MessagePO();
			try{
				IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();
				po = ConverterUtils.convert(message);
				dao.save(po);
			}catch(Exception e){}
			
		}
}
