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
		
		this.calculateDailyFeedConversion(entity);
		this.calculateCumulativeFeedConversion(entity);
		
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
		
		this.calculateDailyFeedConversion(entity);
		this.calculateCumulativeFeedConversion(entity);

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
	
	private void calculateDailyFeedConversion(DailyLog dailyLog) { 
		Optional<DailyLog> entity = dailyLogRepository.findByBatchAndAge(dailyLog.getBatch(), dailyLog.getAge() - 1);
		
		if (entity.isEmpty()) { 
			dailyLog.setDailyFeedConversion(null);
			return;
		}
		
		if (entity.isPresent()) { 
			DailyLog yesterday = entity.get();
			
			Double todaysConsumption = dailyLog.getFeedConsumption();
			Double weightGain = dailyLog.getAverageWeight() - yesterday.getAverageWeight();
			
			if (weightGain > 0.0) { 
				Double dailyFeedConversion = todaysConsumption / weightGain ;
				dailyLog.setDailyFeedConversion(dailyFeedConversion);
			}
			else {
				dailyLog.setDailyFeedConversion(0.0);
			}
		}
	}
	
	private void calculateCumulativeFeedConversion(DailyLog dailyLog) { 
		Double initialWeight = 0.0;
		if (dailyLog.getBatch().getAverageInitialWeight() != null) { 
			initialWeight = dailyLog.getBatch().getAverageInitialWeight();
		}
		else { 
			initialWeight = 0.042;
		}
		
		Double totalWeightGain = dailyLog.getAverageWeight() - initialWeight;
		
		Double totalConsumption = 0.0;
		for (DailyLog dl : dailyLogRepository.findByBatch(dailyLog.getBatch())) { 
			if (dl.getAge() <= dailyLog.getAge()) { 
				totalConsumption += dl.getFeedConsumption();
			}
		}
		
		if (totalWeightGain > 0.0) { 
			Double cumulativeFeedConversion = totalConsumption / totalWeightGain;
			dailyLog.setCumulativeFeedConversion(cumulativeFeedConversion);
		}
		else { 
			dailyLog.setCumulativeFeedConversion(0.0);
		}
	}
}
