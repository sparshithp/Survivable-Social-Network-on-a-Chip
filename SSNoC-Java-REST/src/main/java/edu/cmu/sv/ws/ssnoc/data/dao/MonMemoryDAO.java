package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryCrumbPO;

public class MonMemoryDAO extends BaseDAOImpl implements IMonMemoryDAO {

	@Override
	public void insertMemoryCrumb(MemoryCrumbPO memoryCrumbPO) {
		// TODO Auto-generated method stub
		Log.enter(memoryCrumbPO);
		if (memoryCrumbPO == null) {
			Log.warn("Inside save method with memoryCrumbPO == NULL");
			return;
		}
		
		//usedVolatile, remainingVolatile, usedPersistent, remainingPersistent, createAt

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_MEMORY_CRUMB)) {
			stmt.setString(1, memoryCrumbPO.getUsedVolatile());
			stmt.setString(2, memoryCrumbPO.getRemainingVolatile());
			stmt.setString(3, memoryCrumbPO.getUsedPersistent());
			stmt.setString(4, memoryCrumbPO.getRemainingPersistent());
			stmt.setTimestamp(5, memoryCrumbPO.getCreateAt());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}

	}

	@Override
	public void deleteAllMemoryCrumb() {
		// TODO Auto-generated method stub

			Log.info("deleting all the memory crumbs");

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.DELETE_ALL_MEMORY_CRUMB)) {

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
	public ArrayList<MemoryCrumbPO> getCrumbsByHourInterval(String hour) {
		// TODO Auto-generated method stub
		ArrayList<MemoryCrumbPO> memoryCrumbs = new ArrayList<MemoryCrumbPO>();
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(SQL.SELECT_MEMORY_CRUMB_BY_GIVEN_HOUR);) {
				 
			//Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			int tmpHour = 0 - Integer.parseInt(hour);
			Calendar time = Calendar.getInstance();
			time.add(Calendar.HOUR, tmpHour);		
			Timestamp currentTime = new Timestamp(time.getTimeInMillis());
			stmt.setTimestamp(1, currentTime);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				MemoryCrumbPO po = new MemoryCrumbPO();
				po.setCrumbID(Long.toString(rs.getLong(1)));
				po.setUsedVolatile(rs.getString(2));
				po.setRemainingVolatile(rs.getString(3));
				po.setUsedPersistent(rs.getString(4));
				po.setRemainingPersistent(rs.getString(5));
				if (rs.getTimestamp(6) != null){
					po.setCreateAt(rs.getTimestamp(6));	
				}		
						
				memoryCrumbs.add(po);
			}
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(memoryCrumbs);
		}

		return memoryCrumbs;
	}

}
