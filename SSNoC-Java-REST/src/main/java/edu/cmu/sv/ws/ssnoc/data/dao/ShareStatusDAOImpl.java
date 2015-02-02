package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;

public class ShareStatusDAOImpl extends BaseDAOImpl implements IShareStatusDAO {

	@Override
	public void insert(StatusCrumbPO statusCrumbPO) {
		// TODO Auto-generated method stub
		Log.enter(statusCrumbPO);
		if (statusCrumbPO == null) {
			Log.warn("Inside save method with statusCrumbPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_STATUS_CRUMB)) {
			stmt.setString(1, statusCrumbPO.getUserName());
			stmt.setString(2, statusCrumbPO.getStatusCode());
			stmt.setString(3, statusCrumbPO.getLocationDesc());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

//	@Override
//	public void update(StatusCrumbPO statusCrumbPO) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public List<StatusCrumbPO> loadStatusCrumbsByName(String userName) {
		// TODO Auto-generated method stub
		Log.enter();
		String query = SQL.FIND_ALL_STATUS_CRUMB_BY_USERNAME;

		List<StatusCrumbPO> pos = new ArrayList<StatusCrumbPO>();
		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, userName);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				StatusCrumbPO po = new StatusCrumbPO();
				po.setCrumbId(Long.toString(rs.getLong(1)));
				po.setUserName(rs.getString(2));
				po.setStatusCode(rs.getString(3));
				po.setLocationDesc(rs.getString(4));				
				if (rs.getTimestamp(5) != null){
					po.setCreatedAt(rs.getTimestamp(5).toString());	
				}		
				
				pos.add(po);
			}
			
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(pos);
		}

		return pos;
	}

	@Override
	public StatusCrumbPO findByCrumbId(String crumbId) {
		// TODO Auto-generated method stub
		Log.enter(crumbId);

		if (crumbId == null) {
			Log.warn("Inside findByCrumbId method with NULL crumbId.");
			return null;
		}

		StatusCrumbPO po = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_STATUSCRUMB_BY_ID)) {
			stmt.setLong(1, Long.parseLong(crumbId));

			//List<UserPO> users = processResults(stmt);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			if( Long.toString(rs.getLong(1)) != null){
				po = new StatusCrumbPO();
				po.setCrumbId(Long.toString(rs.getLong(1)));
				po.setUserName(rs.getString(2));
				po.setStatusCode(rs.getString(3));
				po.setLocationDesc(rs.getString(4));				
				if (rs.getTimestamp(5) != null){
					po.setCreatedAt(rs.getTimestamp(5).toString());	
				}

			}
			else{
			  Log.debug("No status crumb  exists with id = " + crumbId);
			}

		} catch (SQLException e) {
			handleException(e);
			Log.exit(po);
		}

		return po;
	}

}
