package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.PerformanceCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.TestMsgPO;

public interface IPerformanceTestDAO {
//	void createPerformanceCrumbTable();
	void createTestMsgTable();
	void deleteTestMsgTable();
	void insertTestMsg(TestMsgPO po);
	List<TestMsgPO> getTestMsg();
	void insertPerformanceCrumb(PerformanceCrumbPO po);
	PerformanceCrumbPO getPerformanceCrumb();
}
