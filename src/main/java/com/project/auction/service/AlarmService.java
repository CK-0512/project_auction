package com.project.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auction.dao.AlarmDao;

@Service
public class AlarmService {
	
	private AlarmDao alarmDao;
	
	@Autowired
	AlarmService(AlarmDao alarmDao){
		this.alarmDao = alarmDao;
	}
	
}