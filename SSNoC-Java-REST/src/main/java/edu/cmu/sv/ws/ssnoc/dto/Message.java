package edu.cmu.sv.ws.ssnoc.dto;

import com.google.gson.Gson;
/**
 * This object contains message information that is responded as part of the REST
 * API request.
 * 
 */
public class Message {
	private String content;
	private String author;
	private String messageType;
	private String target;
	private String postedAt;
	private long messageID;
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}
	
	public String getMessageType()
	{
		return messageType;
	}
	
	public void setTarget(String target)
	{
		this.target = target;
	}
	
	public String getTarget()
	{
		return target;
	}
	
	public void setPostedAt(String postedAt)
	{
		this.postedAt = postedAt;
	}
	
	public String getPostedAt()
	{
		return postedAt;
	}
	
	public void setMessageID(long messageID)
	{
		this.messageID = messageID;
	}
	
	public long getMessageID()
	{
		return messageID;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
