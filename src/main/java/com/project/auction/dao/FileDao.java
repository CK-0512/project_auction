package com.project.auction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.auction.vo.FileVO;


@Mapper
public interface FileDao {

	@Insert("""
			INSERT INTO productFile
				SET regDate = NOW()
					, auctionId = #{auctionId}
					, originName = #{orgName}
					, savedName = #{savedName}
					, savedPath = #{savedPath}
			""")
	void insertFileInfo(int auctionId, String orgName, String savedName, String savedPath);

	@Select("""
			SELECT *
				FROM productFile
				WHERE id = #{fileId}
			""")
	FileVO getFileById(int fileId);

	@Select("""
			SELECT *
				FROM productFile
				WHERE auctionId = #{auctionId}
				LIMIT 1
			""")
	FileVO getAuctionContentsFirstFile(int auctionId);

	@Select("""
			SELECT *
				FROM productFile
				WHERE auctionId = #{auctionId}
			""")
	List<FileVO> getAuctionContentFiles(int auctionId);
	
}