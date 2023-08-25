package com.project.auction.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.RealTime;

@Mapper
public interface RealTimeDao {

	public int getLastDateByMemberId(int memberId);

	public void registRealTime(int memberId, String name, int categoryId, int startBid, String description);

	public int getLastInsertId();

	public RealTime getRealTimeById(int id);

	public void modifyRealTime(int id, int categoryId, int startBid, String name, String description);
	
}
