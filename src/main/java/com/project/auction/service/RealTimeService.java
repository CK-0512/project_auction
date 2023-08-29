package com.project.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.auction.dao.RealTimeDao;
import com.project.auction.vo.RealTime;

public class RealTimeService {
	
	private RealTimeDao realTimeDao;
	
	@Autowired
	RealTimeService(RealTimeDao realTimeDao) {
		this.realTimeDao = realTimeDao;
	}
	
	public int getRealTimeCnt(int categoryId, String searchKeyword, int endStatus, int confirmStatus) {
		return realTimeDao.getRealTimeCnt(categoryId, searchKeyword, endStatus, confirmStatus);
	}

	public List<RealTime> getRealTimeContents(int categoryId, String searchKeyword, int endStatus, int confirmStatus,
			int itemsInAPage, int page) {
		int limitStart = (page - 1) * itemsInAPage;
		
		List<RealTime> list = realTimeDao.getRealTimeContents(categoryId, searchKeyword, endStatus, confirmStatus, limitStart, itemsInAPage);
		
		return list;
	}

	public int getLastDateByMemberId(int loginedMemberId) {
		return realTimeDao.getLastDateByMemberId(loginedMemberId);
	}

	public void registRealTime(int loginedMemberId, String name, int categoryId, int startBid, String body) {
		realTimeDao.registRealTime(loginedMemberId, name, categoryId, startBid, body);
	}

	public int getLastInsertId() {
		return realTimeDao.getLastInsertId();
	}

	public RealTime getRealTimeById(int id) {
		return realTimeDao.getRealTimeById(id);
	}

	public void modifyRealTime(int id, int categoryId, int startBid, String name, String body) {
		realTimeDao.modifyRealTime(id, categoryId, startBid, name, body);
	}

	public void confirmRealTime(int id, String startDate) {
		realTimeDao.confirmRealTime(id, startDate);
	}

	public void rejectRealTime(int id) {
		realTimeDao.rejectRealTime(id);
	}

}