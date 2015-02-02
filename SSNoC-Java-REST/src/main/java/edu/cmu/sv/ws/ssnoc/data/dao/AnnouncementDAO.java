package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.AnnouncementPO;

public class AnnouncementDAO extends BaseDAOImpl implements IAnnouncementDAO {

	@Override
	public void insertAnnouncement(AnnouncementPO announcementPO) {
		// TODO Auto-generated method stub
		Log.enter(announcementPO);
		if (announcementPO == null) {
			Log.warn("Inside save method with announcementPO == NULL");
			return;
		}
		
//		public static final String CREATE_ANNOUNCEMENT_CRUMB = "create table IF NOT EXISTS "
//				+ ANNOUNCEMENT_CRUMB + " ( announcementID IDENTITY PRIMARY KEY,"
//				+ " title VARCHAR(50)," + " content VARCHAR(512)," + " author VARCHAR(50), " + " locationDesc VARCHAR(50), " + " postAt TIMESTAMP )" ;


		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_ANNOUNCEMENT_CRUMB)) {
			stmt.setString(1, announcementPO.getTitle());
			stmt.setString(2, announcementPO.getContent());
			stmt.setString(3, announcementPO.getAuthor());
			stmt.setString(4, announcementPO.getLocationDesc());
			//stmt.setTimestamp(5, announcementPO.getPostAt());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}

	}

	@Override
	public void deleteAllAnnouncements() {
		// TODO Auto-generated method stub

			Log.info("deleting all the announcement crumbs");

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.DELETE_ALL_ANNOUNCEMENT_CRUMB)) {

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
	public ArrayList<AnnouncementPO> getAllAnnouncements() {
		// TODO Auto-generated method stub
		ArrayList<AnnouncementPO> announcementPOs = new ArrayList<AnnouncementPO>();
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(SQL.GET_ANNOUNCEMENT_CRUMB);){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				AnnouncementPO po = new AnnouncementPO();
				po.setTitle(rs.getString(2));
				po.setContent(rs.getString(3));
				po.setAuthor(rs.getString(4));
				po.setLocationDesc(rs.getString(5));
				if (rs.getTimestamp(6) != null){
					po.setPostAt(rs.getTimestamp(6));	
				}		
						
				announcementPOs.add(po);
			}
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(announcementPOs);
		}

		return announcementPOs;
	}

}
