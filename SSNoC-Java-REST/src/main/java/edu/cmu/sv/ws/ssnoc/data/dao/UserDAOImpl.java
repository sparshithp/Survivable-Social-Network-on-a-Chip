package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserProfileAdminPO;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class UserDAOImpl extends BaseDAOImpl implements IUserDAO {
	/**
	 * This method will load users from the DB with specified account status. If
	 * no status information (null) is provided, it will load all users.
	 * 
	 * @return - List of users
	 */
	public List<UserPO> loadUsers() {
		Log.enter();
		String query = SQL.FIND_ALL_USERS;

		List<UserPO> users = new ArrayList<UserPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			users = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(users);
		}
		
		return users;
	}

	private List<UserPO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<UserPO> users = new ArrayList<UserPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				UserPO po = new UserPO();
				po = new UserPO();
				po.setUserId(rs.getLong(1));
				po.setUserName(rs.getString(2));
				po.setPassword(rs.getString(3));
				po.setSalt(rs.getString(4));
				po.setStatus(rs.getString(5));
				po.setLocationDesc(rs.getString(6));
				if (rs.getTimestamp(7) != null){
					po.setLastPostTime(rs.getTimestamp(7).toString());	
				}		
				
				users.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}

	/**
	 * This method with search for a user by his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - UserPO with the user information if a match is found.
	 */
	@Override
	public UserPO findByName(String userName) {
		Log.enter(userName);

		if (userName == null) {
			Log.warn("Inside findByName method with NULL userName.");
			return null;
		}

		UserPO po = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_USER_BY_NAME)) {
			stmt.setString(1, userName.toUpperCase());

			List<UserPO> users = processResults(stmt);

			if (users.size() == 0) {
				Log.debug("No user account exists with userName = " + userName);
			} else {
				po = users.get(0);
			}
			conn.close();
		} catch (SQLException e) {
			handleException(e);
			Log.exit(po);
		}
		
		return po;
	}
	
	/**
	 * This method will search for a user by his user_id in the database. 
	 * 
	 * @param user_id
	 *            - User_id to search for.
	 * 
	 * @return - UserPO with the user information if a match is found.
	 */
	@Override
	public UserPO findById(long userId)
	{
		Log.enter(userId);
		UserPO po = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_USER_BY_ID)) {
			stmt.setLong(1, userId);

			List<UserPO> users = processResults(stmt);

			if (users.size() == 0) {
				Log.debug("No user account exists with userId = " + userId);
			} else {
				po = users.get(0);
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(po);
		}

		return po;
	}

	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	@Override
	public void save(UserPO userPO) {
		Log.enter(userPO);
		if (userPO == null) {
			Log.warn("Inside save method with userPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_USER)) {
			stmt.setString(1, userPO.getUserName());
			stmt.setString(2, userPO.getPassword());
			stmt.setString(3, userPO.getSalt());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
			
			//get user_id and create new default entry in authorization table for that user
			//should be in a batch in real world case
			UserPO savedPo = this.findByName(userPO.getUserName());
			if(savedPo != null){
				IUserProfileAdminDAO dao = DAOFactory.getInstance().getUserProfileAdminDAO();
				
				UserProfileAdminPO po = new UserProfileAdminPO();
				po.setUserId(savedPo.getUserId());
				po.setUserName(savedPo.getUserName());
				po.setSalt(savedPo.getSalt());
				if(po.getUserName().equals("admin"))
				    po.setPrivilegeLevel("administrator");
				else
				    po.setPrivilegeLevel("citizen");//default
				po.setAccountStatus("active");	//default	
				dao.insertNewProfile(po);
			}
			conn.close();
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	
	/**
	 * This method will update the information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be updated.
	 */
	@Override
	public void update(UserPO userPO) {
		Log.enter(userPO);
		if (userPO == null) {
			Log.warn("Inside update method with userPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.UPDATE_USER)) {
			
			//stmt.setString(2, userPO.getPassword());
			stmt.setString(1, userPO.getStatus());
			stmt.setString(2, userPO.getLocationDesc());
			stmt.setString(3, userPO.getUserName());

			int rowCount = stmt.executeUpdate();
			conn.commit();
			conn.close();
			Log.trace("Statement executed, and " + rowCount + " rows updated.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	/**
	 * This method will load all the users with whom a user
	 * has chat with.
	 * @param userName
	 *            - User with whom chat.
	 * @return - List of all users.
	 */
	public List<UserPO> loadChatbuddies(String userName)
	{
		Log.enter(userName);
		if(userName == null)
		{
			Log.warn("user name null");
			return null;
		}
		List<UserPO> users = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_CHATBUDDIES_BY_USER)) {
			stmt.setString(1, userName.toUpperCase());
			stmt.setString(2, userName.toUpperCase());

			users = processResults(stmt);

			if (users.size() == 0) {
				Log.debug("No user chat with userName = " + userName);
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(users);
		}
		
		return users;
	}
}
