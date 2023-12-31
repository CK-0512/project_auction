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
	
	public void deleteFile(int auctionType, int auctionId) {
		List<FileVO> oldFiles = fileDao.getContentsFiles(auctionType, auctionId);
		
		for (FileVO oldFile : oldFiles) {
			String savedPath = oldFile.getSavedPath();
			
			File file = new File(savedPath);
		    
		    fileDao.deleteFileInfo(savedPath);

		    if (file.exists()) {
		        if (file.delete()) {
		            System.out.println("파일 삭제 성공: " + savedPath);
		        } else {
		            System.out.println("파일 삭제 실패: " + savedPath);
		        }
		    } else {
		        System.out.println("파일이 존재하지 않습니다: " + savedPath);
		    }
		}
	}

	public FileVO getContentsFirstFile(int auctionType, int id) {
		return fileDao.getContentsFirstFile(auctionType, id);
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