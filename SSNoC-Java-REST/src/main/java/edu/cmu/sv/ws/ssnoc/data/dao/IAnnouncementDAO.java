package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.ArrayList;

import edu.cmu.sv.ws.ssnoc.data.po.AnnouncementPO;

public interface IAnnouncementDAO {
	void insertAnnouncement(AnnouncementPO announcementPO);
	void deleteAllAnnouncements();
	ArrayList<AnnouncementPO> getAllAnnouncements();
}
