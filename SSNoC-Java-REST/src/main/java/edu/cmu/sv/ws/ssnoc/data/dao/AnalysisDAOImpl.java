package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.dto.AnalysisDTO;

public class AnalysisDAOImpl extends BaseDAOImpl implements AnalysisDAO {
    AnalysisDTO analysisDto = new AnalysisDTO();
	ArrayList<String> allUser = new ArrayList<String>();
    ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
	@Override
	public AnalysisDTO getClusters(int dur) {
		Log.enter();
		String lowerTime = getBoundTime(dur);
		String curTime = getBoundTime(0);
		Log.enter(lowerTime);
		String query;
		if(dur!=0){
		query = "select count(*) from  SSN_MESSAGE,  SSN_USERS as USER1, SSN_USERS as USER2 where SSN_MESSAGE.author = USER1.user_id AND SSN_MESSAGE.target = USER2.user_id AND messageType = 'CHAT' "
				+ "AND ((UPPER(USER1.user_name) = UPPER(?) AND UPPER(USER2.user_name) = UPPER(?)) OR "
				+ "(UPPER(USER2.user_name) = UPPER(?) AND UPPER(USER1.user_name) = UPPER(?)))" 
				+ " AND postedAt  BETWEEN \'" +lowerTime+"\' and \'"+curTime+"\'";
		}
		else {
			query = "select count(*) from  SSN_MESSAGE,  SSN_USERS as USER1, SSN_USERS as USER2 where SSN_MESSAGE.author = USER1.user_id AND SSN_MESSAGE.target = USER2.user_id AND messageType = 'CHAT' "
					+ "AND ((UPPER(USER1.user_name) = UPPER(?) AND UPPER(USER2.user_name) = UPPER(?)) OR "
					+ "(UPPER(USER2.user_name) = UPPER(?) AND UPPER(USER1.user_name) = UPPER(?)))";
		}
		//String query = SQL.FIND_IF_CLOSE;
		String query1 = SQL.FIND_ALL_USER_ID;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query1);) {
				ResultSet rs = stmt.executeQuery();
		 while(rs.next()){
			 allUser.add(rs.getString(1));
		 }
			 
		 }catch (SQLException e) {
				handleException(e);
			}
		
		Connection conn;
		try {
			conn = getConnection();
		
		 
		int count = 1;
		for (String send: allUser){
			ArrayList<String> clust = new ArrayList<String>();
			clust.add(send);
			for (int i = count; i<allUser.size();i++){
				
				PreparedStatement stmt1 = conn.prepareStatement(query);
				stmt1.setString(1, send);
				stmt1.setString(2, allUser.get(i));
				stmt1.setString(3, send);
				stmt1.setString(4, allUser.get(i));
				ResultSet rs1 = stmt1.executeQuery();
				rs1.next(); 
				if (rs1.getInt(1)==0){
					if (!allUser.get(i).equals(send))
					clust.add(allUser.get(i));
				}
			}
			Collections.sort(clust);
			if(!checkIfRepeat(clusters, clust)){
				if(clust.size()>1)
				clusters.add(clust);
				//clusters = getPrunedList(clusters,clust);
			}
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		analysisDto.setClusters(clusters);
		return analysisDto;
	}
	
	
	public String getBoundTime(int dur){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		dur = dur*(-1);
		System.out.println(dur);
		cal.add(Calendar.HOUR, dur);
		String as = dateFormat.format(cal.getTime());
		return as;
	}

	public boolean checkIfRepeat(ArrayList<ArrayList<String>> clusters, ArrayList<String> clust){
		for (ArrayList<String>iter: clusters){
			if(iter.containsAll(clust)||clust.containsAll(iter)) return true;
		}
		return false;
		
	}
	
	/*public ArrayList<ArrayList<String>> getPrunedList(ArrayList<ArrayList<String>> clusters, ArrayList<String> clust){
		for(int i=0; i<clusters.size();i++){
			if(clusters.get(i).containsAll(clust)||clust.containsAll(clusters.get(i))){
				clusters.set(i, maxLength(clusters.get(i),clust));
				return clusters;
			}
		}
		return clusters;
	}
	
	public ArrayList<String> maxLength(ArrayList<String> l1, ArrayList<String> l2){
		if(l1.size()>l2.size()) return l1;
		else return l2;
	}
	
*/
}
