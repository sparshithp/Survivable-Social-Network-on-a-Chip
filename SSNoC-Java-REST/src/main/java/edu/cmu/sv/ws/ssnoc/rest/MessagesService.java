package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserProfileAdminPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;


@Path("/messages")
public class MessagesService extends BaseService{
	/**
	 * This method loads all messages on the public wall.
	 * 
	 * @return - List of messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "messages")
	@Path("/wall")
	public List<Message> loadWallMessages()
	{
		Log.enter();
		List<Message> messages = null;
		try
		{
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().getWallMessages();
			List<UserProfileAdminPO> adminPOs = DAOFactory.getInstance().getUserProfileAdminDAO().getActiveProfiles();
			messages = new ArrayList<Message>();
			for(MessagePO po : messagePOs) 
			{
			    for(UserProfileAdminPO adminPO : adminPOs)
			    {
			        if(adminPO.getUserName().equals(po.getAuthor()))
			        {
			            Message dto = ConverterUtils.convert(po);
		                messages.add(dto);
		                break;
			        }
			    }
			}
		} catch (Exception e) {
				handleException(e);
		} finally {
				Log.exit(messages);
		}
		return messages;
	}
	
	/**
	 * This method loads messages between two people
	 * 
	 * @return - List of messages
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName1}/{userName2}")
	public List<Message> loadPrivateChatMessages(@PathParam("userName1") String userName1, @PathParam("userName2") String userName2)
	{
		Log.enter(userName1, userName2);

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().getPrivateChatMessages(userName1, userName2);

			messages = new ArrayList<Message>();
			for (MessagePO po : messagePOs) {
				Message dto = ConverterUtils.convert(po);
				messages.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
			
}
