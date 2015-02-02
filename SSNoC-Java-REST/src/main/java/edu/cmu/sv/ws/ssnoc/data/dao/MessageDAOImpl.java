package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



//import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

public class MessageDAOImpl extends BaseDAOImpl implements IMessageDAO{
	
	public void save(MessagePO messagePO)
	{
		Log.enter(messagePO);
		if (messagePO == null) {
			Log.warn("Inside save method with userPO == NULL");
			return;
		}

		PreparedStatement stmt;
		try (Connection conn = getConnection();) 
		{
			stmt = conn.prepareStatement(SQL.INSERT_MESSAGE);
			stmt.setString(1, messagePO.getContent());
			stmt.setString(2, messagePO.getAuthor());
			stmt.setString(3, messagePO.getMessageType());
			stmt.setString(4, messagePO.getTarget());
			stmt.setString(5, messagePO.getPostedAt());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
			conn.close();
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	public List<MessagePO> getWallMessages()
	{
		Log.enter();
		List<MessagePO> messages = new ArrayList<MessagePO>();
		PreparedStatement stmt;
		try(Connection conn = getConnection();)
		{
			stmt = conn.prepareStatement(SQL.FIND_MESSAGES_ON_WALL);
			messages = processResults(stmt);
			if (messages.size() == 0) 
				Log.debug("No messages on public wall");
		}catch(SQLException e) {
			handleException(e);
			Log.exit(messages);
		}
		return messages;
	}
	
	public MessagePO getMessageByID(long messageID)
	{
		Log.enter();
		if (messageID < 0) {
			Log.warn("Inside findMessageByID method with NULL userName.");
			return null;
		}
		List<MessagePO> messages = null;
		MessagePO message = null;
		PreparedStatement stmt;
		try(Connection conn = getConnection())
		{
			stmt = conn.prepareStatement(SQL.FIND_MESSAGE_BY_ID);
			stmt.setLong(1, messageID);
			messages = processResults(stmt);
			if (messages.size() != 1) 
				Log.debug("Number of message is not 1");
			else
				message = messages.get(0);
		}catch(SQLException e) {
			handleException(e);
			Log.exit(messages);
		}
		
		return message;
	}
	
	public List<MessagePO> getPrivateChatMessages(String userName1, String userName2)
	{
		Log.enter(userName1, userName2);

		if (userName1 == null || userName2 == null) {
			Log.warn("Insert getPrivateChatMessages method with NULL userName.");
			return null;
		}

		List<MessagePO> messages = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_CHAT_MESSAGES)) {
			stmt.setString(1, userName1.toUpperCase());
			stmt.setString(2, userName2.toUpperCase());
			stmt.setString(3, userName1.toUpperCase());
			stmt.setString(4, userName2.toUpperCase());

			messages = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}
	
	private List<MessagePO> processResults(PreparedStatement stmt)
	{
		Log.enter(stmt);
		if(stmt == null)
		{
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}
		Log.debug("Executing stmt = " + stmt);
		List<MessagePO> messages = new ArrayList<MessagePO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				MessagePO po = new MessagePO();
				po = new MessagePO();
				po.setMessageID(rs.getLong(1));
				po.setContent(rs.getString(2));
				po.setAuthor(rs.getString(3));
				po.setMessageType(rs.getString(4));
				po.setTarget(rs.getString(5));
				po.setPostedAt(rs.getString(6));

				messages.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}
		
		return messages;
	}
}
