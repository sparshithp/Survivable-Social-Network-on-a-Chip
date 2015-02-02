package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Message information in the system.
 * 
 */
public interface IMessageDAO {
	/**
	 * This method will save the information of the message into the database.
	 * 
	 * @param messagePO
	 *            - Message information to be saved.
	 */
	void save(MessagePO messagePO);
	/**
	 * This method with search for all the messages on the wall in the database. 
	 * 
	 * @return - A list of MessagePO of all messages on wall.
	 */
	List<MessagePO> getWallMessages();
	/**
	 * This method with search for all messages between two users. 
	 *  
	 * @param userName1
	 *            - User name for the first user.
	 *            
	 * @param userName2
	 *            - User name for the second user.
	 * 
	 * @return -  A list of MessagePO of all messages between the two users.
	 */
	List<MessagePO> getPrivateChatMessages(String userName1, String userName2);
	
	/**
	 * This method will search for message by ID.
	 * 
	 * @param messageID
	 */
	MessagePO getMessageByID(long messageID);
}
