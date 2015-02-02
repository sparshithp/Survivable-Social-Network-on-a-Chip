package edu.cmu.sv.ws.ssnoc.data.po;

public class StatusCrumbPO {
	private String userName;
	private String statusCode;
	private String locationDesc;
	private String createdAt;
	private String crumbId;
	
	
	public String getCrumbId() {
		return crumbId;
	}
	public void setCrumbId(String crumbId) {
		this.crumbId = crumbId;
	}	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}	

}
