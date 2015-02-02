package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.ArrayList;

import edu.cmu.sv.ws.ssnoc.data.po.MemoryCrumbPO;

public interface IMonMemoryDAO {
	void insertMemoryCrumb(MemoryCrumbPO memoryCrumbPO);
	void deleteAllMemoryCrumb();
	ArrayList<MemoryCrumbPO> getCrumbsByHourInterval(String hour);
}
