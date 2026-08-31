package com.granjas.granjaapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.repositories.DailyLogRepository;

@Service
public class DailyLogService {
	
	private final BatchRepository batchRepository;
	private final DailyLogRepository dailyLogRepository;

	public DailyLogService(DailyLogRepository dailyLogRepository, BatchRepository batchRepository) { 
		this.dailyLogRepository = dailyLogRepository;
		this.batchRepository = batchRepository;
	}
	
	public List<DailyLog> findAll() { 
		return dailyLogRepository.findAllWithWeighings();
	}
	
	public DailyLog findById(Long id) { 
		Optional<DailyLog> obj = dailyLogRepository.findByIdWithWeighings(id);
		return obj.get();
	}
	
	public DailyLog insert(DailyLog dailyLog) { 	
		dailyLog = dailyLogRepository.save(dailyLog);
		updateBatchTotalOfDeaths(dailyLog);
		return dailyLog;
	}
	
	private void updateBatchTotalOfDeaths(DailyLog dailyLog) { 
		int totalOfDeaths = 0;
		for (DailyLog dl : dailyLogRepository.findByBatch(dailyLog.getBatch())) { 
			totalOfDeaths += dl.getDailyMortality();
		}
		
		dailyLog.getBatch().setTotalOfDeaths(totalOfDeaths);
		batchRepository.save(dailyLog.getBatch());
	}
	
	public void delete(Long id) { 
		DailyLog dailyLog = dailyLogRepository.findById(id).get(); 
		dailyLogRepository.delete(dailyLog);
		updateBatchTotalOfDeaths(dailyLog);
	}
}
