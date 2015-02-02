package edu.cmu.sv.ws.ssnoc.test;

import static com.eclipsesource.restfuse.Assert.*;
import static org.junit.Assert.*;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;




@RunWith(HttpJUnitRunner.class)
public class MessageServiceIT {
	
	@Rule
	public Destination destination = new Destination(this,
			"http://localhost:1234/ssnoc");
	
	@Context
	public Response response;
	
	//post a wall message
	@HttpTest(method = Method.POST, path = "/message/tester1", type = MediaType.APPLICATION_JSON, 
			content = "{\"author\":\"qihao\",\"content\":\"Hello\",\"postedAt\":\"2014-11-22 19:30:22\"}")
	public void testSaveWallMessage()
	{
		assertCreated(response);
	}
	
	//post a private message from tester1 to tester2
	@HttpTest(method = Method.POST, path = "/message/tester1/tester2", type = MediaType.APPLICATION_JSON, 
			content = "{\"content\":\"Hello\",\"postedAt\":\"2014-11-22 19:30:22\"}")
	public void testSaveChatMessage()
	{
		System.out.println(response.getBody());
		System.out.println(response.getStatus());
		assertCreated(response);
	}
	
	//get a message whose id is 1
	@HttpTest(method = Method.GET, path = "/message/1")
	public void testGetmessageById() {
		if(response.getBody().equals(""))
			assertNoContent(response);
		else
			assertOk(response);
		//assertNoContent(response);
		//Assert.assertTrue(response.getBody().equals(""));
	}
	
	//get all wall messages
	@HttpTest(method = Method.GET, path = "/messages/wall")
	public void testLoadWallMessages()
	{
		assertOk(response);
	}
	
	//get private messages between tester1 and tester2
	@HttpTest(method = Method.GET, path = "/messages/tester1/tester2")
	public void testLoadPrivateChatMessages()
	{
		assertOk(response);
	}
	
	//get private messages from two users, from whom at least 1 user name is wrong
	//the result should be []
	@HttpTest(method = Method.GET, path = "/messages/tester1000/tester2000")
	public void testLoadPrivateChatMessagesWithWrongUserName()
	{
		Assert.assertEquals("[]", response.getBody());
	}
	/*
	@HttpTest(method = Method.POST, path = "/message/qihao", type = MediaType.APPLICATION_JSON, 
			content = "{\"author\":\"qihao\",\"content\":\"');select * from user where uid = 100;\",\"postedAt\":\"2014-11-22 19:30:22\"}")
	public void testSaveWallMessageWithBug()
	{
		assertBadRequest(response);
	}*/
}
