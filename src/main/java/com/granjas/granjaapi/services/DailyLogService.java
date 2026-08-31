package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.granjas.granjaapi.entities.Batch;
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
		return dailyLogRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("DailyLog not found"));
	}
	
	@Transactional
	public DailyLog insert(DailyLog dailyLog) { 	
		dailyLog = dailyLogRepository.save(dailyLog);
		updateBatchTotalOfDeaths(dailyLog.getBatch());
		return dailyLog;
	}
	
	@Transactional
	public void delete(Long id) { 
		DailyLog dailyLog = dailyLogRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("DailyLog not found"));
		
		Batch batch = dailyLog.getBatch();
		
		dailyLogRepository.delete(dailyLog);
		
		if (batch != null) {
			updateBatchTotalOfDeaths(batch);
		}
	}
	
	private void updateBatchTotalOfDeaths(Batch batch) { 
		int totalOfDeaths = 0;
		for (DailyLog dl : dailyLogRepository.findByBatch(batch)) { 
			totalOfDeaths += dl.getDailyMortality();
		}
		
		batch.setTotalOfDeaths(totalOfDeaths);
		batchRepository.save(batch);
	}
}
