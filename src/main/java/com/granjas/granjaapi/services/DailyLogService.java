package com.granjas.granjaapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.granjas.granjaapi.dto.DailyLogDTO;
import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.entities.enums.BatchStatus;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.repositories.DailyLogRepository;
import com.granjas.granjaapi.services.exceptions.BusinessRuleException;
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
			Batch batch = batchRepository.findById(dto.getBatch().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Batch not found. Id " + dto.getBatch().getId()));
			
			if (batch.getStatus() == BatchStatus.CLOSED) { 
				throw new BusinessRuleException("Business Rule Error: Cannot modify data because this batch is CLOSED");
			}	
			entity.setBatch(batch);
			
			Optional<DailyLog> alreadyExists = dailyLogRepository.findByBatchAndDate(batch, dto.getDate());
			if (alreadyExists.isPresent()) { 
				throw new BusinessRuleException("Business rule exception: today's daily log already exists");
			}
		}
		
		entity = dailyLogRepository.save(entity);
		updateBatchTotalOfDeaths(entity.getBatch());
		return new DailyLogDTO(entity);
	}
	
	@Transactional
	public DailyLogDTO update(Long id, DailyLogDTO dto) { 
		DailyLog entity = dailyLogRepository.getReferenceById(id);
		
		if (entity.getBatch().getStatus() == BatchStatus.CLOSED) { 
			throw new BusinessRuleException("Business Rule Error: Cannot modify data because this batch is CLOSED");
		}
		
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
		
		if (batch.getStatus() == BatchStatus.CLOSED) { 
			throw new BusinessRuleException("Business Rule Error: Cannot modify data because this batch is CLOSED");
		}
		
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
