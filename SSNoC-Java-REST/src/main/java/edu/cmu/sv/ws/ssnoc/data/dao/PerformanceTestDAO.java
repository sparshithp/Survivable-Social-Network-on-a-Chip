package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.PerformanceCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.TestMsgPO;

public class PerformanceTestDAO extends BaseDAOImpl implements IPerformanceTestDAO {

//	@Override
//	public void createPerformanceCrumbTable() {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void createTestMsgTable() {
		// TODO Auto-generated method stub
		Log.enter();

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
	
				Log.info("Creating tables in database ...");

				boolean status = stmt.execute(SQL.CREATE_TEST_MSG);
				Log.debug("Query execution completed with status: "
							+ status);
				Log.info("Tables created successfully");
			
		}catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	@Override
	public void deleteTestMsgTable() {
		// TODO Auto-generated method stub
		Log.enter();

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
	
				Log.info("Trying to delete TestMsg table in database ...");

				boolean status = stmt.execute(SQL.DELETE_TEST_MSG);
				Log.debug("Query execution completed with status: "
							+ status);
				Log.info("Tables deleted successfully");
			
		}catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	@Override
	public void insertTestMsg(TestMsgPO po) {
		// TODO Auto-generated method stub
		Log.enter(po);
		if (po == null) {
			Log.warn("Inside save method with TestMsgPO == NULL");
			return;
		}
		
		//usedVolatile, remainingVolatile, usedPersistent, remainingPersistent, createAt

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_TEST_MSG)) {
			stmt.setString(1, po.getAuthor());
			stmt.setString(2, po.getTarget());
			stmt.setString(3, po.getContent());
			stmt.setString(4, po.getMessageType());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows of test msg inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	@Override
	public List<TestMsgPO> getTestMsg() {
		// TODO Auto-generated method stub
		ArrayList<TestMsgPO> pos = new ArrayList<TestMsgPO>();
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(SQL.SELECT_ALL_TEST_MSG);) {
				 
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TestMsgPO po = new TestMsgPO();
				po.setMessageID(Long.toString(rs.getLong(1)));
				po.setAuthor(rs.getString(2));
				po.setTarget(rs.getString(3));
				po.setContent(rs.getString(4));
				po.setMessageType(rs.getString(5));
				po.setPostedAt(rs.getTimestamp(6));	
						
				pos.add(po);
			}
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(pos);
		}

		return pos;
	}

	public PerformanceCrumbPO getPerformanceCrumb() {
		// TODO Auto-generated method stub
		PerformanceCrumbPO po = null;
		try (Connection conn = getConnection();
			 PreparedStatement stmt = conn.prepareStatement(SQL.GET_PERFORMANCE_CRUMB);) {
				 
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				po = new PerformanceCrumbPO();
				po.setCrumbID(rs.getLong(1));
				po.setPostsTotal(rs.getInt(2));
				po.setGetsTotal(rs.getInt(3));
				po.setPostsPerSecond(rs.getInt(4));
				po.setGetsPerSecond(rs.getInt(5));
			}
			
		} catch (SQLException e) {
			handleException(e);
			Log.exit(po);
		}

		return po;
	}
	
	public void insertPerformanceCrumb(PerformanceCrumbPO po) {
		// TODO Auto-generated method stub
		Log.enter(po);
		if (po == null) {
			Log.warn("Inside save method with PerformanceCrumbPO == NULL");
			return;
		}	

		try (Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_PERFORMANCE_CRUMB)) {
			stmt.setInt(1, po.getPostsTotal());
			stmt.setInt(2, po.getGetsTotal());
			stmt.setInt(3, po.getPostsPerSecond());
			stmt.setInt(4, po.getGetsPerSecond());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows of performance crumb inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}
	
}
