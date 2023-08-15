package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Article;

@Mapper
public interface ArticleDao {
	
	public void writeArticle(int memberId, int boardId, String title, String body);

	public Article getArticleById(int id);

	public void modifyArticle(int id, String title, String body);

	public void deleteArticle(int id);

	public List<Article> getArticles(int boardId, String searchKeyword, String searchKeywordType, int limitStart, int itemsInAPage);

	public int getLastInsertId();

	public int getArticlesCnt(int boardId, String searchKeyword, String searchKeywordType);

	public int increaseHitCnt(int id);
}