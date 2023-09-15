package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.auction.vo.FileVO;

@Mapper
public interface FileDao {

	public void insertFileInfo(int auctionType, int auctionId, String orgName, String savedName, String savedPath);
	
	public void deleteFileInfo(String savedPath);

	public FileVO getFileById(int fileId);

	public List<FileVO> getContentsFiles(int auctionType, int auctionId);

	public FileVO getContentsFirstFile(int auctionType, int auctionId);

}