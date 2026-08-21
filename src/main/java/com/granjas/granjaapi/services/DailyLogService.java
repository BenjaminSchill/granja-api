package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.repositories.DailyLogRepository;

@Service
public class DailyLogService {
	
	private final DailyLogRepository dailyLogRepository;

	public DailyLogService(DailyLogRepository dailyLogRepository) { 
		this.dailyLogRepository = dailyLogRepository;
	}
	
	public List<DailyLog> findAll() { 
		return dailyLogRepository.findAllWithWeighings();
	}
}
