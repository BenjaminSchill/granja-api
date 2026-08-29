package com.granjas.granjaapi.services;

import java.util.List;
import java.util.Optional;

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
	
	public DailyLog findById(Long id) { 
		Optional<DailyLog> obj = dailyLogRepository.findByIdWithWeighings(id);
		return obj.get();
	}
	
	public DailyLog insert(DailyLog dailyLog) { 
		return dailyLogRepository.save(dailyLog);
	}
}
