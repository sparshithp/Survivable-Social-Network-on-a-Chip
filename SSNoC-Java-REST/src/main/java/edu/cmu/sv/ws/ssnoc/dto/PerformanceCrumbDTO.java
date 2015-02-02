package edu.cmu.sv.ws.ssnoc.dto;

import java.sql.Timestamp;

public class PerformanceCrumbDTO {
	private long crumbID;
	private int postsTotal;
	private int getsTotal;
	private int postsPerSecond;
	private int getsPerSecond;
	private Timestamp createAt;
	
	public long getCrumbID() {
		return crumbID;
	}
	public void setCrumbID(long crumbID) {
		this.crumbID = crumbID;
	}
	public int getPostsTotal() {
		return postsTotal;
	}
	public void setPostsTotal(int postsTotal) {
		this.postsTotal = postsTotal;
	}
	public int getGetsTotal() {
		return getsTotal;
	}
	public void setGetsTotal(int getsTotal) {
		this.getsTotal = getsTotal;
	}
	public int getPostsPerSecond() {
		return postsPerSecond;
	}
	public void setPostsPerSecond(int postsPerSecond) {
		this.postsPerSecond = postsPerSecond;
	}
	public int getGetsPerSecond() {
		return getsPerSecond;
	}
	public void setGetsPerSecond(int getsPerSecond) {
		this.getsPerSecond = getsPerSecond;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
}
