package com.project.auction.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.auction.dao.FileDao;
import com.project.auction.vo.Auction;
import com.project.auction.vo.Cart;
import com.project.auction.vo.FileVO;

@Service
public class FileService {
	
	@Value("${file.dir}")
	private String fileDir;

	private FileDao fileDao;

	@Autowired
	public FileService(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public void saveFile(int auctionType, MultipartFile file, int auctionId) throws IOException {

		if (file.isEmpty()) {
			return;
		}

		String orgName = file.getOriginalFilename();

		String uuid = UUID.randomUUID().toString();

		String extension = orgName.substring(orgName.lastIndexOf("."));

		String savedName = uuid + extension;

		String savedPath = fileDir + "/" + savedName;

		fileDao.insertFileInfo(auctionType, auctionId, orgName, savedName, savedPath);

		file.transferTo(new File(savedPath));
	}

	public List<FileVO> getContentsFirstFile(int auctionType, List<Auction> auctionContents) {
		
		List<FileVO> files = new ArrayList<>();
		
		for (Auction auctionContent : auctionContents) {
			files.add(fileDao.getContentsFirstFile(auctionType, auctionContent.getId()));
		}
		
		return files;
	}

	public FileVO getFileById(int id) {
		return fileDao.getFileById(id);
	}

	public List<FileVO> getContentsFiles(int auctionType, int auctionId) {
		return fileDao.getContentsFiles(auctionType, auctionId);
	}

	public List<FileVO> getCartsFirstFile(int auctionType, List<Cart> carts) {
		
		List<FileVO> files = new ArrayList<>();
		
		for (Cart cart : carts) {
			files.add(fileDao.getContentsFirstFile(auctionType, cart.getAuctionId()));
		}
		
		return files;
	}
}