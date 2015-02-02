package edu.cmu.sv.ws.ssnoc.rest;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.h2.util.StringUtils;

import edu.cmu.sv.ws.ssnoc.common.exceptions.ServiceException;
import edu.cmu.sv.ws.ssnoc.common.exceptions.UnauthorizedUserException;
import edu.cmu.sv.ws.ssnoc.common.exceptions.ValidationException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.dto.Message;


/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to message.
 * 
 */

@Path("/message")
public class MessageService extends BaseService{

	
	/**
	 * This method posts a message on public wall
	 * 
	 * @param userName
	 *            - Name of a user
	 * @param message
	 *            - message related data
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public Response saveWallMessage(@PathParam("userName") String userName, Message message)
	{
		Log.enter(userName, message);
		Message resp = new Message();
		MessagePO po = new MessagePO();
		try
		{
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();
			po = ConverterUtils.convert(message);
			po.setMessageType("WALL");
			po.setAuthor(userName);
			po.setTarget(userName);
			dao.save(po);
			resp = ConverterUtils.convert(po);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@"+resp.getAuthor()+";"+resp.getTarget());
		} catch (Exception e) {
			handleException(e); 
		} finally {
			Log.exit();
		}
		
		return created(resp);
	}
	
	
	/**
	 * This method is used to login a user.
	 * 
	 * @param sendingUserName
	 *            - name of sending user
	 * @param receivingUserName
	 *            - name of receiving user
	 * 
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{sendignUserName}/{receivingUserName}")
	public Response saveChatMessage(@PathParam("sendignUserName") String sendignUserName, 
			@PathParam("receivingUserName") String receivingUserName, Message message)
	{
		Log.enter(sendignUserName, receivingUserName);
		Message resp = new Message();
		MessagePO po = new MessagePO();
		try
		{
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();
			po = ConverterUtils.convert(message);
			po.setMessageType("CHAT");
			po.setAuthor(sendignUserName);
			po.setTarget(receivingUserName);
			dao.save(po);
			resp = ConverterUtils.convert(po);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@"+resp.getAuthor()+";"+resp.getTarget());
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}
		
		return created(resp);
	}
	
	/**
	 * All message information related to a particular ID.
	 * 
	 * @param messageID
	 *            - message ID
	 * 
	 * @return - Details of the message
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{messageID}")
	public Message getMessageByID(@PathParam("messageID") long messageID) {
		Log.enter(messageID);

		Message message = null;
		try {
			IMessageDAO messageDao = DAOFactory.getInstance().getMessageDAO();
			MessagePO po = messageDao.getMessageByID(messageID);

			if (po != null) {
				Log.trace("Message exists.");
				message = ConverterUtils.convert(po);
			}
			else {
				Log.trace("No such ID!!");
			}

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return message;
	}
	
}
