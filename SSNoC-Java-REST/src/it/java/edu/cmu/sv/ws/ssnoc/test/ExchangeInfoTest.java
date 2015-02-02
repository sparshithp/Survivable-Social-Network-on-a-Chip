package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.dao.BaseDAOImpl;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.MessagesService;

public class ExchangeInfoTest extends BaseDAOImpl {
	@Before
    public void setUpTestData() throws Exception{

        Connection conn= getConnection();

        PreparedStatement stmtInsertUser = conn.prepareStatement(SQL.INSERT_USER);
        stmtInsertUser.setString(1, "A");
        stmtInsertUser.setString(2, null);
        stmtInsertUser.setString(3, null);
        stmtInsertUser.execute();
        stmtInsertUser.setString(1, "B");
        stmtInsertUser.setString(2, null);
        stmtInsertUser.setString(3, null);
        stmtInsertUser.execute();
	}
	
	@Test
    public void testloadWallMessages() {

        MessageService EMS = new MessageService();
        String username = "A";
        Message message = new Message();
        message.setAuthor("A");
        message.setContent("YooHoo");
        Response res = EMS.saveWallMessage(username, message);

        MessagesService EIS = new MessagesService();
        List<Message> list;
        list = EIS.loadWallMessages();

        assertEquals(list.get(0).getContent(),"YooHoo");
    }
	
	@Test(expected=Exception.class)
    public void saveWallMessageNullUser() {

        MessageService EMS = new MessageService();
        String username = null;
        Message message = new Message();
        message.setAuthor("A");
        message.setContent("YooHoo");
        Response res = EMS.saveWallMessage(username, message);

        MessagesService EIS = new MessagesService();
        List<Message> list;
        list = EIS.loadWallMessages();

        assertEquals(list.get(0).getContent(),"YooHoo");
    }
	
	@Test(expected=NullPointerException.class)
    public void testNullMessages() {

        MessageService EMS = new MessageService();
        String username = "A";
        Message message = null;
        message.setAuthor("A");
        message.setContent("YooHoo");
        Response res = EMS.saveWallMessage(username, message);
        MessagesService EIS = new MessagesService();
        List<Message> list;
        list = EIS.loadWallMessages();
    }
	
	@Test
    public void testloadChatMessages() {
        MessageService EMS = new MessageService();
        String username1 = "A";
        String username2 = "B";
        Message message = new Message();
        message.setAuthor("A");
        message.setContent("YooHoo");
        Response res = EMS.saveChatMessage(username1, username2, message);

        MessagesService EIS = new MessagesService();
        List<Message> list;
        list = EIS.loadPrivateChatMessages(username1, username2);

        assertEquals(list.get(0).getAuthor(),"A");
        assertEquals(list.get(0).getTarget(),"B");
        assertEquals(list.get(0).getContent(),"YooHoo");
    }
	
	@Test(expected=Exception.class)
    public void sqlErrorChatMessages() {
        MessageService EMS = new MessageService();
        String username1 = "AADSA";
        String username2 = "B";
        Message message = new Message();
        message.setAuthor("A");
        message.setContent("YooHoo");
        Response res = EMS.saveChatMessage(username1, username2, message);

        MessagesService EIS = new MessagesService();
        List<Message> list;
        list = EIS.loadPrivateChatMessages(username1, username2);
        list.get(0).getAuthor();
    }
	
	@Test(expected=Exception.class)
    public void nullUser() {
        MessageService EMS = new MessageService();
        String username1 = null;
        String username2 = "B";
        Message message = new Message();
        message.setAuthor("A");
        message.setContent("YooHoo");
        Response res = EMS.saveChatMessage(username1, username2, message);

        MessagesService EIS = new MessagesService();
        List<Message> list;
        list = EIS.loadPrivateChatMessages(username1, username2);
    }

	
	
	
	@After
	public void clear() throws SQLException{
		Connection conn= getConnection();
        String dropTable = "Delete from SSN_USERS where user_name='A' or user_name ='B'";
        PreparedStatement stmtDrop = conn.prepareStatement(dropTable);
        stmtDrop.execute();	
        String delMsg = "Delete from SSN_MESSAGE";
        PreparedStatement msgDrop = conn.prepareStatement(delMsg);
        stmtDrop.execute();
        conn.close();
	}
	
	
}
