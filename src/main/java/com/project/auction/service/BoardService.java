package com.project.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.BoardDao;
import com.project.auction.vo.Board;

@Service
public class BoardService {
	
	private BoardDao boardDao;
	
	@Autowired
	BoardService(BoardDao boardDao){
		this.boardDao = boardDao;
	}
	
	public List<Board> getBoardById(int boardId) {
		return boardDao.getBoardById(boardId);
	}
}