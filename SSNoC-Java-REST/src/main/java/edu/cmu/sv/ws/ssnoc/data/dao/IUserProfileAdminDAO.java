package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.ArrayList;

import edu.cmu.sv.ws.ssnoc.data.po.UserProfileAdminPO;

public interface IUserProfileAdminDAO {
	void insertNewProfile(UserProfileAdminPO po); 
	void updateProfile(UserProfileAdminPO po);
	UserProfileAdminPO getProfile(long userId);
	UserProfileAdminPO getProfile(String userName);
	void deleteAllAuthorizationTable();
	ArrayList<UserProfileAdminPO> getActiveProfiles();
}
