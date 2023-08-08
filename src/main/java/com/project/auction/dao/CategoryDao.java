package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Category;

@Mapper
public interface CategoryDao {

	public void doRegistInterest(int memberId, int categoryId);

	public int getCategoryId(String category);

	public List<Category> getInterestCategories(int memberId);

	public List<Category> getCategories();

	public void doDeleteInterest(int loginedMemberId, int categoryId);
	
	public List<Category> getCategoryById(int categoryId);

	
}
