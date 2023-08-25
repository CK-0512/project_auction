package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.FileVO;


@Mapper
public interface FileDao {

	void insertFileInfo(int auctionType, int auctionId, String orgName, String savedName, String savedPath);

	FileVO getFileById(int fileId);

	List<FileVO> getContentsFiles(int auctionType, int auctionId);

	FileVO getContentsFirstFile(int auctionType, int auctionId);

}