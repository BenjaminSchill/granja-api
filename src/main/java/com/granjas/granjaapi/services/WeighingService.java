package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.granjas.granjaapi.dto.WeighingDTO;
import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.entities.Weighing;
import com.granjas.granjaapi.repositories.DailyLogRepository;
import com.granjas.granjaapi.repositories.WeighingRepository;
import com.granjas.granjaapi.services.exceptions.ResourceNotFoundException;

@Service
public class WeighingService {
	
	private final DailyLogRepository dailyLogRepository;
	private final WeighingRepository weighingRepository;

	public WeighingService(WeighingRepository weighingRepository, DailyLogRepository dailyLogRepository) { 
		this.weighingRepository = weighingRepository;
		this.dailyLogRepository = dailyLogRepository;
	}
	
	public List<WeighingDTO> findAll() { 
		List<Weighing> list = weighingRepository.findAll();
		return list.stream().map(x -> new WeighingDTO(x)).toList();
	}
	
	public WeighingDTO findById(Long id) { 
		Weighing entity = weighingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
		return new WeighingDTO(entity);
	}
	
	@Transactional
	public WeighingDTO insert(WeighingDTO dto) { 
		Weighing entity = new Weighing();
		entity.setWeighingPoint(dto.getWeighingPoint());
		entity.setTotalInBox(dto.getTotalInBox());
		entity.setWeightInBox(dto.getWeightInBox());
		
		if (dto.getDailyLogId() != null) { 
			DailyLog dailyLog = new DailyLog(); 
			dailyLog.setId(dto.getDailyLogId());
			entity.setDailyLog(dailyLog);
		}
		entity = weighingRepository.save(entity);
		updateDailyLogCalculations(dto.getDailyLogId(), null);
		return new WeighingDTO(entity);
	}
	
	@Transactional
	public WeighingDTO update(Long id, WeighingDTO dto) { 
		Weighing entity = weighingRepository.getReferenceById(id);
		entity.setWeighingPoint(dto.getWeighingPoint());
		entity.setTotalInBox(dto.getTotalInBox());
		entity.setWeightInBox(dto.getWeightInBox());
		weighingRepository.save(entity);
		updateDailyLogCalculations(entity.getDailyLog().getId(), null);
		return new WeighingDTO(entity);
	}
	
	@Transactional
	public void delete(Long id) { 
		Weighing weighing = weighingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
		
		Long dailyLogId = weighing.getDailyLog().getId();
		
		weighingRepository.delete(weighing);
		
		updateDailyLogCalculations(dailyLogId, id);
	}
		
	private void updateDailyLogCalculations(Long dailyLogId, Long excludedWeighingId) { 
		DailyLog dailyLog = dailyLogRepository.findById(dailyLogId)
				.orElseThrow(() -> new RuntimeException("DailyLog not found"));
		
		double totalWeight = 0.0;
		int totalChickens = 0; 
		
		for (Weighing w : dailyLog.getWeighings()) {
			if (excludedWeighingId == null || !w.getId().equals(excludedWeighingId)) {
				totalWeight += w.getWeightInBox();
				totalChickens += w.getTotalInBox();
			}
		}
		
		if (totalChickens > 0) {
		    double averageWeight = totalWeight / (double) totalChickens;
		    dailyLog.setAverageWeight(averageWeight);
		    dailyLog.setTotalWeight(totalWeight);
		}
		else {
		    dailyLog.setAverageWeight(0.0);
		    dailyLog.setTotalWeight(0.0);
		}
		dailyLogRepository.save(dailyLog);
	}
}
