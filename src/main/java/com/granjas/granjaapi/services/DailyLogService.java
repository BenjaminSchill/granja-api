package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.repositories.DailyLogRepository;
import com.granjas.granjaapi.services.exceptions.ResourceNotFoundException;

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
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
	}
	
	@Transactional
	public DailyLog insert(DailyLog dailyLog) { 	
		dailyLog = dailyLogRepository.save(dailyLog);
		updateBatchTotalOfDeaths(dailyLog.getBatch());
		return dailyLog;
	}
	
	@Transactional
	public DailyLog update(Long id, DailyLog dailyLog) { 
		DailyLog entity = dailyLogRepository.getReferenceById(id);
		entity.setAge(dailyLog.getAge());
		entity.setFeedConsumption(dailyLog.getFeedConsumption());
		entity.setWaterConsumption(dailyLog.getWaterConsumption());
		entity.setDailyMortality(dailyLog.getDailyMortality());
		entity = dailyLogRepository.save(entity);
		updateBatchTotalOfDeaths(entity.getBatch());
		return entity;
		}
	
	@Transactional
	public void delete(Long id) { 
		DailyLog dailyLog = dailyLogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
		
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
