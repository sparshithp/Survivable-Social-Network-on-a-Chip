package edu.cmu.sv.ws.ssnoc.rest;

import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;

public class TestService {
	public static void addTestUser(String userName)
	{
		User user = new User();
		user.setUserName(userName);
		user.setPassword("123456");
		try{
			IUserDAO dao = DAOFactory.getInstance().getUserDAO();
			UserPO existingUser = dao.findByName(user.getUserName());
			if(existingUser == null)
			{
				UserPO po = ConverterUtils.convert(user);
				po = SSNCipher.encryptPassword(po);
				System.out.println(po.getUserName());
				dao.save(po);
			}
		}catch(Exception e)
		{
			
		}
	}
	
	public static void postTestWallMessage(String content, String author)
	{
		Message message = new Message();
		message.setAuthor(author);
		message.setContent(content);
		message.setTarget(author);
		message.setMessageType("WALL");
		message.setPostedAt("2014-02-02 12:12:12");
		MessagePO po = new MessagePO();
		try{
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();
			po = ConverterUtils.convert(message);
			dao.save(po);
		}catch(Exception e)
		{
			
		}
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
		}catch(Exception e)
		{
			
		}
	}
	
}
