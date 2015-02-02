package edu.cmu.sv.ws.ssnoc.data.po;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 * 
 */
public class UserProfileAdminPO {
	private String userName;
	private String password;
	private String salt;
	private long userId;
	private String accountStatus;
	private String privilegeLevel;
	private Timestamp changeAt;
	


	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getSalt() {
		return salt;
	}



	public void setSalt(String salt) {
		this.salt = salt;
	}

	
	
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public String getAccountStatus() {
		return accountStatus;
	}



	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}



	public String getPrivilegeLevel() {
		return privilegeLevel;
	}



	public void setPrivilegeLevel(String privilegeLevel) {
		this.privilegeLevel = privilegeLevel;
	}



	public Timestamp getChangeAt() {
		return changeAt;
	}



	public void setChangeAt(Timestamp changeAt) {
		this.changeAt = changeAt;
	}



	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
