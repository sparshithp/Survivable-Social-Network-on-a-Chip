package edu.cmu.sv.ws.ssnoc.data.po;

import java.sql.Timestamp;

public class AnnouncementPO {
	private String title;
	private String content;
	private String author;
	private String locationDesc;
	private Timestamp postAt;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public Timestamp getPostAt() {
		return postAt;
	}
	public void setPostAt(Timestamp postAt) {
		this.postAt = postAt;
	}
	
	
	
}
