package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.UserProfileAdminPO;

public class UserProfileAdminDAO extends BaseDAOImpl implements
		IUserProfileAdminDAO {

	@Override
	/* should be called when adding new user
	 * 
	 * */
	public void insertNewProfile(UserProfileAdminPO po) {
		// TODO Auto-generated method stub
		Log.enter(po);
		if (po == null) {
			Log.warn("Inside save method with UserProfileAdminPO == NULL");
			return;
		}
		
		//BACK END SQL: (userId, accountStatus, privilegeLevel, changeAt) values (?, ?, ?, CURRENT_TIMESTAMP() )";
		if(po.getUserId() != 0){
			try (Connection conn = getConnection();
					PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_AUTHORIZATION_WITH_USERID)) {
					stmt.setLong(1, po.getUserId());
					stmt.setString(2, po.getAccountStatus());
					stmt.setString(3, po.getPrivilegeLevel());

					int rowCount = stmt.executeUpdate();
					Log.trace("Statement executed, and " + rowCount + " rows inserted.");
				} catch (SQLException e) {
					handleException(e);
				} finally {
					Log.exit();
				}
		}

	}

	@Override
	public void deleteAllAuthorizationTable() {
		// TODO Auto-generated method stub

			Log.info("deleting all authorization");

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.DELETE_ALL_AUTHORIZATION)) {

			int rowCount = stmt.executeUpdate();
			//conn.commit();
			//conn.close();
			Log.trace("Statement executed, and " + rowCount + " rows deleted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}
	
	@Override
	public void updateProfile(UserProfileAdminPO po) {
		// TODO Auto-generated method stub
		Log.enter(po);
		if (po == null) {
			Log.warn("Inside update method with UserProfileAdminPO == NULL");
			return;
		}

		if( po.getUserId() != 0 && po.getUserName() != null ){
			
			try{	Connection conn = getConnection();										
					PreparedStatement stmt = conn.prepareStatement(SQL.UPDATE_AUTHORIZATION_BY_USERID);
					stmt.setString(1, po.getAccountStatus());
					stmt.setString(2, po.getPrivilegeLevel());
					stmt.setLong(3, po.getUserId());					
					int rowCount = stmt.executeUpdate();

					//can use statement batch
					PreparedStatement stmt1 = conn.prepareStatement(SQL.UPDATE_USER_NAME_PWD);
					//po = SSNCipher.encryptPassword(po);	//modified by qihao. I put it into UerPrifileAdinService.java
					stmt1.setString(1, po.getUserName());
					stmt1.setString(2, po.getPassword());
					stmt1.setString(3, po.getSalt()); 
					stmt1.setLong(4, po.getUserId());					
					int rowCount1 = stmt1.executeUpdate();	
					
					conn.commit();
					conn.close();
					Log.trace("Statement executed, and " + rowCount + rowCount1 + " rows updated.");
				} catch (SQLException e) {
					handleException(e);
				} finally {
					Log.exit();
				}			
		}	
		
	}

	@Override
	public UserProfileAdminPO getProfile(long userId) {
		// TODO Auto-generated method stub
		UserProfileAdminPO po = null;
		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.GET_AUTHORIZATION_BY_USERID);) {
			stmt.setLong(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				po = new UserProfileAdminPO();
				po.setUserId(rs.getLong(1));
				po.setUserName(rs.getString(2));
				po.setPassword(rs.getString(3));
				po.setAccountStatus(rs.getString(4));
				po.setPrivilegeLevel(rs.getString(5));	
			}
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(po);
		}
		return po;
	}
	
	@Override
	public UserProfileAdminPO getProfile(String userName) {
		// TODO Auto-generated method stub
		UserProfileAdminPO po = null;
		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.GET_AUTHORIZATION_BY_USERNAME);) {
			stmt.setString(1, userName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				po = new UserProfileAdminPO();
				po.setUserId(rs.getLong(2));
				po.setAccountStatus(rs.getString(3));
				po.setPrivilegeLevel(rs.getString(4));
				po.setChangeAt(rs.getTimestamp(5));	
			}
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(po);
		}
		return po;
	}
	
	@Override
	public ArrayList<UserProfileAdminPO> getActiveProfiles(){
		ArrayList<UserProfileAdminPO> pos = new ArrayList<UserProfileAdminPO>();
		UserProfileAdminPO po = null;
		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.GET_ALL_ACTIVE_USERPROFILE);) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				po = new UserProfileAdminPO();
				po.setUserId(rs.getLong(1));
				po.setUserName(rs.getString(2));
				pos.add(po);
			}
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(pos);
		}
		return pos;
	}
	
}
