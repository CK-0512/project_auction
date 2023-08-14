package com.project.auction.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.auction.service.FileService;
import com.project.auction.vo.FileVO;

@Controller
public class FileUploadController {

	private FileService fileService;

	@Autowired
	public FileUploadController(FileService fileService) {
		this.fileService = fileService;
	}


	@RequestMapping("/usr/home/file/{fileId}")
	@ResponseBody
	public Resource downloadImage(@PathVariable("fileId") int id, Model model) throws IOException {

		FileVO fileVo = fileService.getFileById(id);

		return new UrlResource("file:" + fileVo.getSavedPath()); 
	}
}