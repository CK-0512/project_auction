package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.RealTime;

@Mapper
public interface RealTimeDao {
	
	public int getRealTimeCnt(int categoryId, String searchKeyword, int endStatus, int confirmStatus);

	public List<RealTime> getRealTimeContents(int categoryId, String searchKeyword, int endStatus, int confirmStatus,
			int limitStart, int itemsInAPage);

	public int getLastDateByMemberId(int memberId);

	public void registRealTime(int memberId, String name, int categoryId, String hopeDate, int startBid, String description);

	public int getLastInsertId();

	public RealTime getRealTimeById(int id);

	public void modifyRealTime(int id, int categoryId, int startBid, String name, String description);

	public void deleteRealTime(int id);
	
	public void confirmRealTime(int id, String startDate);

	public void rejectRealTime(int id);

	public int isExist(int memberId);
	
}
