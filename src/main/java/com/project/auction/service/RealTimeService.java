package com.project.auction.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.auction.dao.RealTimeDao;
import com.project.auction.vo.RealTime;

public class RealTimeService {
	
	private RealTimeDao realTimeDao;
	
	@Autowired
	RealTimeService(RealTimeDao realTimeDao) {
		this.realTimeDao = realTimeDao;
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

}
