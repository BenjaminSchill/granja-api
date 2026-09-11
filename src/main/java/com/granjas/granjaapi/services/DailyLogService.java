package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.granjas.granjaapi.dto.DailyLogDTO;
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
	
	public List<DailyLogDTO> findAll() { 
		List<DailyLog> list = dailyLogRepository.findAllWithWeighings();
		return list.stream().map(x -> new DailyLogDTO(x)).toList();
	}
	
	public DailyLogDTO findById(Long id) { 
		DailyLog entity = dailyLogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
		return new DailyLogDTO(entity);
	}
	
	@Transactional
	public DailyLogDTO insert(DailyLogDTO dto) { 
		DailyLog entity = new DailyLog();
		entity.setAge(dto.getAge());
		entity.setDailyMortality(dto.getDailyMortality());
		entity.setTotalWeight(dto.getTotalWeight());
		entity.setAverageWeight(dto.getAverageWeight());
		entity.setFeedConsumption(dto.getFeedConsumption());
		entity.setWaterConsumption(dto.getWaterConsumption());
		entity.setDate(dto.getDate());
		
		if (dto.getBatch() != null) { 
			Batch batch = new Batch();
			batch.setId(dto.getBatch().getId());
			entity.setBatch(batch);
		}
		
		entity = dailyLogRepository.save(entity);
		updateBatchTotalOfDeaths(entity.getBatch());
		return new DailyLogDTO(entity);
	}
	
	@Transactional
	public DailyLogDTO update(Long id, DailyLogDTO dto) { 
		DailyLog entity = dailyLogRepository.getReferenceById(id);
		entity.setAge(dto.getAge());
		entity.setFeedConsumption(dto.getFeedConsumption());
		entity.setWaterConsumption(dto.getWaterConsumption());
		entity.setDailyMortality(dto.getDailyMortality());
		entity = dailyLogRepository.save(entity);
		updateBatchTotalOfDeaths(entity.getBatch());
		return new DailyLogDTO(entity);
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
