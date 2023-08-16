package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.auction.vo.FileVO;


@Mapper
public interface FileDao {

	@Insert("""
			INSERT INTO auctionFile
				SET regDate = NOW()
					, auctionType = #{auctionType}
					, auctionId = #{auctionId}
					, originName = #{orgName}
					, savedName = #{savedName}
					, savedPath = #{savedPath}
			""")
	void insertFileInfo(int auctionType, int auctionId, String orgName, String savedName, String savedPath);

	@Select("""
			SELECT *
				FROM auctionFile
				WHERE id = #{fileId}
			""")
	FileVO getFileById(int fileId);

	@Select("""
			SELECT *
				FROM auctionFile
				WHERE auctionId = #{auctionId}
				LIMIT 1
			""")
	FileVO getAuctionContentsFirstFile(int auctionId);

	@Select("""
			SELECT *
				FROM auctionFile
				WHERE auctionId = #{auctionId}
			""")
	List<FileVO> getAuctionContentFiles(int auctionId);
	
}