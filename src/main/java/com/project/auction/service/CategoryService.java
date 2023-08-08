package com.project.auction.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.CategoryDao;
import com.project.auction.vo.Category;

@Service
public class CategoryService {
	
	private CategoryDao categoryDao;
	
	@Autowired
	CategoryService(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public void doRegistInterest(int memberId, List<String> categories) {
		
		for(String category : categories) {
			int categoryId = categoryDao.getCategoryId(category);
			
			categoryDao.doRegistInterest(memberId, categoryId);
		}
		
	}

	public List<Category> getInterestCategories(int loginedMemberId) {	
		return categoryDao.getInterestCategories(loginedMemberId);
	}

	public List<Category> getCategories() {
		return categoryDao.getCategories();
	}

	public void doModifyInterest(int loginedMemberId, List<String> categories, List<Category> beforeInterestCategories) {
		
		List<Integer> newInterestCategoryIds = new ArrayList<>();

	    for (String category : categories) {
	        int categoryId = categoryDao.getCategoryId(category);
	        newInterestCategoryIds.add(categoryId);

	        boolean categoryExists = false;
	        for (Category beforeInterestCategory : beforeInterestCategories) {
	            if (beforeInterestCategory.getId() == categoryId) {
	                categoryExists = true;
	                break;
	            }
	        }

	        if (!categoryExists) {
	            categoryDao.doRegistInterest(loginedMemberId, categoryId);
	        }
	    }

	    for (Category beforeInterestCategory : beforeInterestCategories) {
	        if (!newInterestCategoryIds.contains(beforeInterestCategory.getId())) {
	            categoryDao.doDeleteInterest(loginedMemberId, beforeInterestCategory.getId());
	        }
	    }
	}

	public List<Category> getCategoryById(int categoryId) {
		return categoryDao.getCategoryById(categoryId);
	}
}
