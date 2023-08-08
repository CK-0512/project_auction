package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.Board;

@Mapper
public interface BoardDao {

	public List<Board> getBoardById(int boardId);
	
}